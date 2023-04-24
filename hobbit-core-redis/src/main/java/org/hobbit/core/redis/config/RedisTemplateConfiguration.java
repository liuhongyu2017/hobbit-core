package org.hobbit.core.redis.config;

import org.hobbit.core.redis.cache.HobbitRedis;
import org.hobbit.core.redis.listener.TransactionCacheListener;
import org.hobbit.core.redis.props.HobbitRedisProperties;
import org.hobbit.core.redis.serializer.RedisKeySerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * RedisTemplate  配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/23
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@AutoConfiguration(before = {RedisAutoConfiguration.class})
@EnableConfigurationProperties(HobbitRedisProperties.class)
public class RedisTemplateConfiguration implements HobbitRedisSerializerConfigAble {

  /**
   * value 值 序列化
   *
   * @param properties 配置
   * @return RedisSerializer
   */
  @Bean
  @ConditionalOnMissingBean(RedisSerializer.class)
  @Override
  public RedisSerializer<Object> redisSerializer(HobbitRedisProperties properties) {
    return defaultRedisSerializer(properties);
  }

  @Bean(name = "redisTemplate")
  @ConditionalOnMissingBean(name = "redisTemplate")
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory,
      RedisSerializer<Object> redisSerializer
  ) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    // key 序列化
    RedisKeySerializer keySerializer = new RedisKeySerializer();
    redisTemplate.setKeySerializer(keySerializer);
    redisTemplate.setHashKeySerializer(keySerializer);
    // value 序列化
    redisTemplate.setValueSerializer(redisSerializer);
    redisTemplate.setHashValueSerializer(redisSerializer);
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    return redisTemplate;
  }

  @Bean(name = "valueOperations")
  @ConditionalOnMissingBean(ValueOperations.class)
  public ValueOperations<?, ?> valueOperations(RedisTemplate<?, ?> redisTemplate) {
    return redisTemplate.opsForValue();
  }

  @Bean
  public HobbitRedis hobbitRedis(RedisTemplate<String, Object> redisTemplate,
      StringRedisTemplate stringRedisTemplate) {
    return new HobbitRedis(redisTemplate, stringRedisTemplate);
  }

  @Bean
  public TransactionCacheListener transactionCacheListener(HobbitRedis hobbitRedis) {
    return new TransactionCacheListener(hobbitRedis);
  }
}
