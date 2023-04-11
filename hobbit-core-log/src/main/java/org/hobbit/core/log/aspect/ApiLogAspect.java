package org.hobbit.core.log.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hobbit.core.log.annotation.ApiLog;
import org.hobbit.core.log.publisher.ApiLogPublisher;

/**
 * 操作日志使用spring event异步入库
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
@Slf4j
@Aspect
public class ApiLogAspect {

  @Around("@annotation(apiLog)")
  public Object around(ProceedingJoinPoint point, ApiLog apiLog) throws Throwable {
    //获取类名
    String className = point.getTarget().getClass().getName();
    //获取方法
    String methodName = point.getSignature().getName();
    // 发送异步日志事件
    long beginTime = System.currentTimeMillis();
    //执行方法
    Object result = point.proceed();
    //执行时长(毫秒)
    long time = System.currentTimeMillis() - beginTime;
    //记录日志
    ApiLogPublisher.publishEvent(methodName, className, apiLog, time);
    return result;
  }

}
