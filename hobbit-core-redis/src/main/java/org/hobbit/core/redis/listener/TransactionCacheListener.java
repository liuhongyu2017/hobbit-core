package org.hobbit.core.redis.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.redis.cache.CacheUtil;
import org.hobbit.core.tool.utils.Func;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * <p>事务监听器，用来清空缓存</p>
 * <pre>
 *  BEFORE_COMMIT：事务提交前清空缓存，如果缓存清空失败抛出异常，事务回滚，保证缓存和数据库一致性
 *  AFTER_COMMIT：事务提交后清空缓存，保证缓存和数据库一致性
 * </pre>
 *
 * @author lhy
 * @version 1.0.0 2023/4/24
 */
@RequiredArgsConstructor
@Slf4j
public class TransactionCacheListener {

  private final CacheUtil cacheUtil;

  /**
   * 监听事务提交前，清空对应 key 的缓存
   */
  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void clearCacheListenerBefore(TransactionCacheEvent event) {
    cacheUtil.evict(event.getCacheName(), event.getKeyPrefix(), event.getKey());
  }

  /**
   * 监听事务提交后，清空对应 key 的缓存
   */
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void clearCacheListenerAfter(TransactionCacheEvent event) {
    cacheUtil.evict(event.getCacheName(), event.getKeyPrefix(), event.getKey());
  }
}
