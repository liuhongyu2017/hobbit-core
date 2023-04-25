package org.hobbit.core.redis.config;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * @author lhy
 * @version 1.0.0 2023/4/24
 */
public class RedisAutoCacheManager extends RedisCacheManager {

  public RedisAutoCacheManager(RedisCacheWriter cacheWriter,
      RedisCacheConfiguration defaultCacheConfiguration) {
    super(cacheWriter, defaultCacheConfiguration);
  }
}
