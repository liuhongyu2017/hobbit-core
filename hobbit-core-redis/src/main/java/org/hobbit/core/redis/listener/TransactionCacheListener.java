package org.hobbit.core.redis.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.hobbit.core.redis.cache.HobbitRedis;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 事务缓存监听器，事务结束后清空缓存
 *
 * @author lhy
 * @version 1.0.0 2023/4/24
 */
@RequiredArgsConstructor
@Slf4j
public class TransactionCacheListener {

  private final HobbitRedis hobbitRedis;

  /**
   * 监听事务完成
   */
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
  public void cacheListener(TransactionCacheEvent event) {
    String[] keys = event.getKeys();
    if (ArrayUtils.isNotEmpty(keys)) {
      hobbitRedis.del(keys);
    }
  }
}
