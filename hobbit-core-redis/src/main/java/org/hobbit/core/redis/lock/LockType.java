package org.hobbit.core.redis.lock;

import lombok.Getter;

/**
 * 锁类型
 *
 * @author lhy
 * @version 1.0.0 2023/4/24
 */
@Getter
public enum LockType {

  /**
   * 重入锁
   */
  REENTRANT,
  /**
   * 公平锁
   */
  FAIR,
}
