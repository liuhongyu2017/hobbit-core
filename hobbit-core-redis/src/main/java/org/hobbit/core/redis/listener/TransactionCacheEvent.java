package org.hobbit.core.redis.listener;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author lhy
 * @version 1.0.0 2023/4/24
 */
@Getter
public class TransactionCacheEvent extends ApplicationEvent {

  /**
   * 缓存 key
   */
  String cacheName;
  String keyPrefix;
  Object key;

  public TransactionCacheEvent(Object source, String cacheName, String keyPrefix, Object key) {
    super(source);
    this.cacheName = cacheName;
    this.keyPrefix = keyPrefix;
    this.key = key;
  }

  public TransactionCacheEvent(Object source, String cacheName) {
    super(source);
    this.cacheName = cacheName;
  }
}
