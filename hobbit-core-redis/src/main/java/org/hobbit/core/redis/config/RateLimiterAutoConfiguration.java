package org.hobbit.core.redis.config;

import java.util.List;
import org.hobbit.core.redis.props.HobbitRateLimiterProperties;
import org.hobbit.core.redis.ratelimiter.RedisRateLimiterAspect;
import org.hobbit.core.redis.ratelimiter.RedisRateLimiterClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * 基于 redis 的限流配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/23
 */
@AutoConfiguration
@EnableConfigurationProperties(HobbitRateLimiterProperties.class)
@ConditionalOnProperty(value = "hobbit.rate-limiter.enabled", havingValue = "true")
public class RateLimiterAutoConfiguration {

  @SuppressWarnings({"unchecked", "rawtypes"})
  private RedisScript<List<Long>> redisRateLimiterScript() {
    DefaultRedisScript redisScript = new DefaultRedisScript<>();
    redisScript.setScriptSource(
        new ResourceScriptSource(
            new ClassPathResource("META-INF/scripts/hobbit_rate_limiter.lua")));
    redisScript.setResultType(List.class);
    return redisScript;
  }

  @Bean
  @ConditionalOnMissingBean
  public RedisRateLimiterClient redisRateLimiter(
      StringRedisTemplate redisTemplate, Environment environment) {
    RedisScript<List<Long>> redisRateLimiterScript = redisRateLimiterScript();
    return new RedisRateLimiterClient(redisTemplate, redisRateLimiterScript, environment);
  }

  @Bean
  @ConditionalOnMissingBean
  public RedisRateLimiterAspect redisRateLimiterAspect(RedisRateLimiterClient rateLimiterClient) {
    return new RedisRateLimiterAspect(rateLimiterClient);
  }
}
