package org.hobbit.core.redis.config;

import org.hobbit.core.redis.cache.CacheUtil;
import org.hobbit.core.redis.listener.TransactionCacheListener;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author lhy
 * @version 1.0.0 2023/4/24
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@AutoConfiguration()
public class HobbitRedisCacheAutoConfiguration {

  @Bean
  public TransactionCacheListener transactionCacheListener(CacheUtil cacheUtil) {
    return new TransactionCacheListener(cacheUtil);
  }
}
