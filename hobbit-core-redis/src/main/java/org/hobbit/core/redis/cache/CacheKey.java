package org.hobbit.core.redis.cache;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 缓存 key 封装
 *
 * @author lhy
 * @version 1.0.0 2023/4/23
 */
@Getter
@ToString
@AllArgsConstructor
public class CacheKey {

  public CacheKey(String key) {
    this.key = key;
  }

  /**
   * redis key
   */
  private final String key;
  /**
   * 超时时间 秒
   */
  private Duration expire;
}
