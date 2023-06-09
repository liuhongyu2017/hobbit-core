package org.hobbit.core.jwt.config;

import lombok.RequiredArgsConstructor;
import org.hobbit.core.jwt.JwtUtil;
import org.hobbit.core.jwt.props.JwtProperties;
import org.hobbit.core.jwt.serializer.JwtRedisKeySerializer;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * Jwt 配置类
 *
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
@Configuration
@RequiredArgsConstructor
@AutoConfigureAfter(JwtRedisConfiguration.class)
@EnableConfigurationProperties({JwtProperties.class})
public class JwtConfiguration implements SmartInitializingSingleton {

  private final JwtProperties jwtProperties;
  private final RedisConnectionFactory redisConnectionFactory;

  @Override
  public void afterSingletonsInstantiated() {
    // redisTemplate 实例化
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    JwtRedisKeySerializer redisKeySerializer = new JwtRedisKeySerializer();
    JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
    // key 序列化
    redisTemplate.setKeySerializer(redisKeySerializer);
    redisTemplate.setHashKeySerializer(redisKeySerializer);
    // value 序列化
    redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
    redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.afterPropertiesSet();
    JwtUtil.setJwtProperties(jwtProperties);
    JwtUtil.setRedisTemplate(redisTemplate);
  }

}
