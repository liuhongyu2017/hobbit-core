package org.hobbit.core.redis.lock;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.tool.function.CheckedSupplier;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * @author lhy
 * @version 1.0.0 2023/4/24
 */
@Slf4j
@RequiredArgsConstructor
public class RedisLockClient implements IRedisLockClient {

  private final RedissonClient redissonClient;
  /**
   * redis 锁 key 前缀
   */
  private static final String REDIS_KEY_PREFIX = "lock:";

  @Override
  public boolean tryLock(String lockName, LockType lockType, long waitTime, long leaseTime,
      TimeUnit timeUnit) throws InterruptedException {
    RLock lock = getLock(REDIS_KEY_PREFIX + lockName, lockType);
    // 尝试获取锁
    return lock.tryLock(waitTime, leaseTime, timeUnit);
  }

  @Override
  public void unLock(String lockName, LockType lockType) {
    RLock lock = getLock(REDIS_KEY_PREFIX + lockName, lockType);
    // 仅在已经锁定和当前线程持有锁时解锁
    if (lock.isLocked() && lock.isHeldByCurrentThread()) {
      lock.unlock();
    }
  }

  @Override
  public <T> T lock(String lockName, LockType lockType, long waitTime, long leaseTime,
      TimeUnit timeUnit, CheckedSupplier<T> supplier) {
    try {
      boolean result = this.tryLock(lockName, lockType, waitTime, leaseTime, timeUnit);
      if (result) {
        return supplier.get();
      }
    } catch (Throwable e) {
      e.printStackTrace();
    } finally {
      this.unLock(lockName, lockType);
    }
    return null;
  }

  private RLock getLock(String lockName, LockType lockType) {
    RLock lock;
    if (LockType.REENTRANT == lockType) {
      lock = redissonClient.getLock(lockName);
    } else {
      lock = redissonClient.getFairLock(lockName);
    }
    return lock;
  }
}
