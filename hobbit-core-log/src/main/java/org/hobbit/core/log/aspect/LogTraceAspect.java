package org.hobbit.core.log.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hobbit.core.log.utils.LogTraceUtil;

/**
 * 为异步方法添加traceId
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
@Aspect
public class LogTraceAspect {

  @Pointcut("@annotation(org.springframework.scheduling.annotation.Async)")
  public void logPointCut() {
  }

  @Around("logPointCut()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    try {
      LogTraceUtil.insert();
      return point.proceed();
    } finally {
      LogTraceUtil.remove();
    }
  }
}
