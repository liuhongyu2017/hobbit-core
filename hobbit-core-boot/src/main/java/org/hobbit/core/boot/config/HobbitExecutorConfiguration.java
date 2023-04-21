package org.hobbit.core.boot.config;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.boot.error.ErrorType;
import org.hobbit.core.boot.util.ErrorUtil;
import org.hobbit.core.context.HobbitContext;
import org.hobbit.core.context.HobbitRunnableWrapper;
import org.hobbit.core.launch.props.HobbitProperties;
import org.hobbit.core.log.constant.EventConstant;
import org.hobbit.core.log.event.ErrorLogEvent;
import org.hobbit.core.log.model.LogError;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ErrorHandler;

/**
 * 异步处理
 *
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@Slf4j
@Configuration
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class HobbitExecutorConfiguration implements AsyncConfigurer {

  private final HobbitContext hobbitContext;
  private final HobbitProperties hobbitProperties;
  private final ApplicationEventPublisher publisher;

  @Bean
  public TaskExecutorCustomizer taskExecutorCustomizer() {
    return taskExecutor -> {
      taskExecutor.setThreadNamePrefix("async-task-");
      // 执行装饰器，包装 runnable 方法
      taskExecutor.setTaskDecorator(HobbitRunnableWrapper::new);
      taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    };
  }

  @Bean
  public TaskSchedulerCustomizer taskSchedulerCustomizer() {
    return taskExecutor -> {
      taskExecutor.setThreadNamePrefix("async-scheduler");
      taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
      // 设置错误处理器
      taskExecutor.setErrorHandler(
          new HobbitErrorHandler(hobbitContext, hobbitProperties, publisher));
    };
  }

  /**
   * 捕获异步引发的为捕获异常
   */
  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return new HobbitAsyncUncaughtExceptionHandler(hobbitContext, hobbitProperties, publisher);
  }

  @RequiredArgsConstructor
  private static class HobbitErrorHandler implements ErrorHandler {

    private final HobbitContext hobbitContext;
    private final HobbitProperties hobbitProperties;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void handleError(@NonNull Throwable error) {
      HobbitExecutorConfiguration.log.error("Unexpected scheduler exception", error);
      LogError logError = new LogError();
      logError.setParams(ErrorType.SCHEDULER.getType());
      logError.setServiceId(this.hobbitProperties.getName());
      logError.setEnv(this.hobbitProperties.getEnv());
      logError.setRequestUri(this.hobbitContext.getRequestId());
      ErrorUtil.initErrorInfo(error, logError);
      Map<String, Object> event = new HashMap<>(16);
      event.put(EventConstant.EVENT_LOG, logError);
      this.eventPublisher.publishEvent(new ErrorLogEvent(event));
    }
  }

  @RequiredArgsConstructor
  private static class HobbitAsyncUncaughtExceptionHandler implements
      AsyncUncaughtExceptionHandler {

    private final HobbitContext hobbitContext;
    private final HobbitProperties hobbitProperties;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void handleUncaughtException(@NonNull Throwable error, @NonNull Method method,
        @NonNull Object... params) {
      log.error("Unexpected exception occurred invoking async method: {}", method, error);
      LogError logError = new LogError();
      // 服务信息、环境、异常类型
      logError.setParams(ErrorType.ASYNC.getType());
      logError.setEnv(hobbitProperties.getEnv());
      logError.setServiceId(hobbitProperties.getName());
      logError.setRequestUri(hobbitContext.getRequestId());
      // 堆栈信息
      ErrorUtil.initErrorInfo(error, logError);
      Map<String, Object> event = new HashMap<>(16);
      event.put(EventConstant.EVENT_LOG, logError);
      eventPublisher.publishEvent(new ErrorLogEvent(event));
    }
  }
}
