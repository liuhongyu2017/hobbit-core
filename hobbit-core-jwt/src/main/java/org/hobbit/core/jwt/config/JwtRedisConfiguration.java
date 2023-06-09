package org.hobbit.core.jwt.config;

import java.time.Duration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * RedisTemplate 配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
@Order
@EnableCaching
@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class JwtRedisConfiguration {

  @Bean("redisCacheManager")
  @ConditionalOnMissingBean(name = "redisCacheManager")
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofHours(1));
    return RedisCacheManager
        .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
        .cacheDefaults(redisCacheConfiguration).build();
  }

}
