package org.hobbit.core.redis.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * <p>>锁注解，基于 redisson</p
 * <pre>
 *   redisson 支持的锁很多，适合注解形式的只有重入锁、公平锁
 *   1. 可重入锁（Reentrant Lock）
 *   2. 公平锁（Fair Lock）
 *   3. 联锁（MultiLock）
 *   4. 红锁（RedLock）
 *   5. 读写锁（ReadWriteLock）
 * </pre>
 *
 * @author lhy
 * @version 1.0.0 2023/4/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisLock {

  /**
   * 分布式锁的 key，必须：请保持唯一性
   */
  String value();

  /**
   * 分布式锁参数，可选，支持 spring pl # 读取方法参数和 @ 读取 spring bean
   */
  String param() default "";

  /**
   * 等待锁超时时间，默认 30s
   */
  long waitTime() default 30;

  /**
   * 自动解锁时间，自动解锁一定要大于方法执行时间，否则会导致锁提前释放，默认 100s
   */
  long leaseTime() default 100;

  /**
   * 时间单位，默认为秒
   */
  TimeUnit timeUnit() default TimeUnit.SECONDS;

  /**
   * 默认公平锁
   */
  LockType type() default LockType.FAIR;
}
