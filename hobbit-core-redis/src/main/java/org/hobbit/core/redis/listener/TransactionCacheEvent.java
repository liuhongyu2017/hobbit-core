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
  private final String[] keys;

  public TransactionCacheEvent(Object source, String... keys) {
    super(source);
    this.keys = keys;
  }
}
