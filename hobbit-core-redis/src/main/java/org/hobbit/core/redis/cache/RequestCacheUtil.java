package org.hobbit.core.redis.cache;

import com.google.common.cache.Cache;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;

/**
 * <p>请求缓存，生命周期当前 request 请求，结束后销毁</p>
 * <p>通过 ThreadLocal 实现</p>
 *
 * @author lhy
 * @version 1.0.0 2023/4/28
 */
@RequiredArgsConstructor
public class RequestCacheUtil implements ServletRequestListener {

  private final HttpServletRequest request;
  private final Cache<String, Map<String, Object>> cache;

  @Override
  public void requestInitialized(ServletRequestEvent sre) {
    ServletRequestListener.super.requestInitialized(sre);
  }

  @Override
  public void requestDestroyed(ServletRequestEvent sre) {
    cache.invalidate(getRequestId());
  }

  private String getRequestId() {
    return request.getRequestId();
  }

  private Map<String, Object> getCache() {
    try {
      return cache.get(getRequestId(), HashMap::new);
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T get(String key, Function<String, T> mappingFunction) {
    return (T) getCache().computeIfAbsent(key, mappingFunction);
  }

  public void remove(String key) {
    getCache().remove(key);
  }
}
