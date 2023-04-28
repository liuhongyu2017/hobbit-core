package org.hobbit.core.redis.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;

/**
 * <p>请求缓存，生命周期当前 request 请求，结束后销毁</p>
 * <p>通过 ThreadLocal 实现</p>
 *
 * @author lhy
 * @version 1.0.0 2023/4/28
 */
public class RequestCache implements ServletRequestListener {

  private static final ThreadLocal<Cache<String, Object>> cacheHolder = new ThreadLocal<>();

  @Override
  public void requestInitialized(ServletRequestEvent sre) {
    ServletRequestListener.super.requestInitialized(sre);
    cacheHolder.set(CacheBuilder.newBuilder()
        // 并发数量设置为cpu核心数
        .concurrencyLevel(Runtime.getRuntime().availableProcessors())
        // 初始容量
        .initialCapacity(100)
        // 最大容量
        .maximumSize(1000)
        .build());
  }

  @Override
  public void requestDestroyed(ServletRequestEvent sre) {
    ServletRequestListener.super.requestDestroyed(sre);
    cacheHolder.remove();
  }

  public static Cache<String, Object> getCache() {
    return cacheHolder.get();
  }
}
