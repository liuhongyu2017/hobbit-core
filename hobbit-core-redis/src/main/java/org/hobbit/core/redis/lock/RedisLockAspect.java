package org.hobbit.core.redis.lock;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hobbit.core.tool.spel.HobbitExpressionEvaluator;
import org.hobbit.core.tool.utils.CharPool;
import org.hobbit.core.tool.utils.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.Assert;

/**
 * 锁 AOP
 *
 * @author lhy
 * @version 1.0.0 2023/4/24
 */
@Aspect
@RequiredArgsConstructor
public class RedisLockAspect implements ApplicationContextAware {

  private ApplicationContext applicationContext;
  /**
   * 表达式处理
   */
  private static final HobbitExpressionEvaluator EVALUATOR = new HobbitExpressionEvaluator();
  /**
   * redis 限流服务
   */
  private final IRedisLockClient redisLockClient;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  /**
   * AOP 环切 注解 @RedisLock
   */
  @Around("@annotation(redisLock)")
  public Object aroundRedisLock(ProceedingJoinPoint point, RedisLock redisLock) {
    String lockName = redisLock.value();
    Assert.hasText(lockName, "@RedisLock value must have length; it must not be null or empty");
    // el 表达式
    String lockParam = redisLock.param();
    // 表达式不为空
    String lockKey;
    if (StringUtil.isNotBlank(lockParam)) {
      String evalAsText = evalLockParam(point, lockParam);
      lockKey = lockName + CharPool.COLON + evalAsText;
    } else {
      lockKey = lockName;
    }
    LockType lockType = redisLock.type();
    long waitTime = redisLock.waitTime();
    long leaseTime = redisLock.leaseTime();
    TimeUnit timeUnit = redisLock.timeUnit();
    return redisLockClient.lock(lockKey, lockType, waitTime, leaseTime, timeUnit, point::proceed);
  }

  /**
   * 计算参数表达式
   *
   * @param point     ProceedingJoinPoint
   * @param lockParam lockParam
   * @return 结果
   */
  private String evalLockParam(ProceedingJoinPoint point, String lockParam) {
    MethodSignature ms = (MethodSignature) point.getSignature();
    Method method = ms.getMethod();
    Object[] args = point.getArgs();
    Object target = point.getTarget();
    Class<?> targetClass = target.getClass();
    EvaluationContext context = EVALUATOR.createContext(method, args, target, targetClass,
        applicationContext);
    AnnotatedElementKey elementKey = new AnnotatedElementKey(method, targetClass);
    return EVALUATOR.evalAsText(lockParam, elementKey, context);
  }
}
