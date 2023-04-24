package org.hobbit.core.redis.config;

import org.hobbit.core.redis.lock.IRedisLockClient;
import org.hobbit.core.redis.lock.RedisLockAspect;
import org.hobbit.core.redis.lock.RedisLockClient;
import org.hobbit.core.redis.props.HobbitLockProperties;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 锁自动化配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/24
 */
@AutoConfiguration
@ConditionalOnClass(RedissonClient.class)
@EnableConfigurationProperties(HobbitLockProperties.class)
@ConditionalOnProperty(value = "hobbit.lock.enabled", havingValue = "true")
public class HobbitLockAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public IRedisLockClient redisLockClient(RedissonClient redissonClient) {
    return new RedisLockClient(redissonClient);
  }

  @Bean
  @ConditionalOnMissingBean
  public RedisLockAspect redisLockAspect(IRedisLockClient redisLockClient) {
    return new RedisLockAspect(redisLockClient);
  }
}
