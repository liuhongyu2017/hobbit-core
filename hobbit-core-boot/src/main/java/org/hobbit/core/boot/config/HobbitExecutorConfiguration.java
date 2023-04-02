package org.hobbit.core.boot.config;

import java.util.concurrent.ThreadPoolExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.context.HobbitContext;
import org.hobbit.core.context.HobbitRunnableWrapper;
import org.hobbit.core.launch.props.HobbitProperties;
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
      taskExecutor.setErrorHandler(
          new HobbitErrorHandler(hobbitContext, hobbitProperties, publisher));
    };
  }

  @RequiredArgsConstructor
  private static class HobbitErrorHandler implements ErrorHandler {

    private final HobbitContext hobbitContext;
    private final HobbitProperties hobbitProperties;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void handleError(@NonNull Throwable error) {

    }
  }
}
