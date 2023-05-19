package org.hobbit.core.redis.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.hobbit.core.redis.cache.CacheUtil;
import org.hobbit.core.redis.cache.RequestCacheUtil;
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

  /**
   * request 生命周期的 cache
   */
  @Bean
  public Cache<String, Map<String, Object>> requestCache() {
    return CacheBuilder.newBuilder()
        // 并发数量设置为cpu核心数
        .concurrencyLevel(Runtime.getRuntime().availableProcessors())
        // 初始容量
        .initialCapacity(100)
        // 基于时间剔除，剔除超过两分钟的缓存
        .expireAfterAccess(2, TimeUnit.MINUTES)
        .build();
  }

  @Bean
  public RequestCacheUtil requestCacheUtil(
      HttpServletRequest request, Cache<String, Map<String, Object>> cache
  ) {
    return new RequestCacheUtil(request, cache);
  }
}
