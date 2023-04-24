package org.hobbit.core.redis.lock;

import java.util.concurrent.TimeUnit;
import org.hobbit.core.tool.function.CheckedSupplier;

/**
 * 锁客户端
 *
 * @author lhy
 * @version 1.0.0 2023/4/24
 */
public interface IRedisLockClient {

  /**
   * 尝试获取锁
   *
   * @param lockName  锁名
   * @param lockType  锁类型
   * @param waitTime  等待时间
   * @param leaseTime 自动解锁时间
   * @param timeUnit  时间单位
   * @return true 成功 false 失败
   */
  boolean tryLock(String lockName, LockType lockType, long waitTime, long leaseTime,
      TimeUnit timeUnit) throws InterruptedException;

  /**
   * 解锁
   *
   * @param lockName 锁名
   * @param lockType 锁类型
   */
  void unLock(String lockName, LockType lockType);

  /**
   * 自动获取锁执行后的方法
   *
   * @param lockName  锁名
   * @param lockType  锁类型
   * @param waitTime  等待时间
   * @param leaseTime 自动解锁时间
   * @param timeUnit  时间单位
   * @param supplier  获取锁后回调
   */
  <T> T lock(String lockName, LockType lockType, long waitTime, long leaseTime, TimeUnit timeUnit,
      CheckedSupplier<T> supplier);

  /**
   * 公平锁
   *
   * @param lockName  锁名
   * @param waitTime  等待时间
   * @param leaseTime 自动解锁时间
   * @param supplier  获取锁后回调
   */
  default <T> T lockFair(String lockName, long waitTime, long leaseTime,
      CheckedSupplier<T> supplier) {
    return lock(lockName, LockType.FAIR, waitTime, leaseTime, TimeUnit.SECONDS, supplier);
  }

  /**
   * 可重入锁
   *
   * @param lockName  锁名
   * @param waitTime  等待时间
   * @param leaseTime 自动解锁时间
   * @param supplier  获取锁后回调
   */
  default <T> T lockReentrant(String lockName, long waitTime, long leaseTime,
      CheckedSupplier<T> supplier) {
    return lock(lockName, LockType.REENTRANT, waitTime, leaseTime, TimeUnit.SECONDS, supplier);
  }
}
