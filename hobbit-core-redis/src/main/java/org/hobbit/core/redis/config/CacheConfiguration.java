package org.hobbit.core.redis.config;

import org.hobbit.core.redis.cache.CacheUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

/**
 * Cache 配置类
 *
 * @author lhy
 * @version 1.0.0 2023/4/25
 */
@EnableCaching
@AutoConfiguration
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class CacheConfiguration {

  @Bean
  public CacheUtil cacheUtil(CacheManager cacheManager) {
    return new CacheUtil(cacheManager);
  }
}
