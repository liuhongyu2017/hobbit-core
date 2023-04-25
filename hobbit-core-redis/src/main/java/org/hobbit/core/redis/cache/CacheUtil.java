package org.hobbit.core.redis.cache;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.hobbit.core.tool.constant.HobbitConstant;
import org.hobbit.core.tool.utils.ClassUtil;
import org.hobbit.core.tool.utils.Func;
import org.hobbit.core.tool.utils.ObjectUtil;
import org.hobbit.core.tool.utils.ReflectUtil;
import org.hobbit.core.tool.utils.StringPool;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

/**
 * 缓存工具
 *
 * @author lhy
 * @version 1.0.0 2023/4/25
 */
@SuppressWarnings("unchecked")
@RequiredArgsConstructor
public class CacheUtil {

  private final CacheManager cacheManager;

  /**
   * 获取缓存工具
   */
  public CacheManager getCacheManager() {
    return this.cacheManager;
  }

  /**
   * 获取缓存对象
   */
  public Cache getCache(String cacheName) {
    return getCacheManager().getCache(cacheName);
  }

  /**
   * 获取缓存
   *
   * @param cacheName 缓存名
   * @param keyPrefix 缓存键前缀
   * @param key       缓存键值
   * @return value
   */
  public Object get(String cacheName, String keyPrefix, Object key) {
    if (Func.hasEmpty(cacheName, keyPrefix, key)) {
      return null;
    }
    Cache.ValueWrapper valueWrapper = getCache(cacheName).get(getKey(keyPrefix, key));
    if (Func.isEmpty(valueWrapper)) {
      return null;
    }
    return valueWrapper.get();
  }

  /**
   * 获取缓存
   *
   * @param cacheName 缓存名
   * @param keyPrefix 缓存键前缀
   * @param key       缓存键值
   * @param type      类型
   * @param <T>       泛型
   * @return value
   */
  public <T> T get(String cacheName, String keyPrefix, Object key, @Nullable Class<T> type) {
    if (Func.hasEmpty(cacheName, keyPrefix, key)) {
      return null;
    }
    return getCache(cacheName).get(getKey(keyPrefix, key), type);
  }

  /**
   * 获取缓存
   *
   * @param cacheName   缓存名
   * @param keyPrefix   缓存键前缀
   * @param key         缓存键值
   * @param valueLoader 重载对象
   * @param <T>         泛型
   * @return value
   */
  public <T> T get(String cacheName, String keyPrefix, Object key, Callable<T> valueLoader) {
    if (Func.hasEmpty(cacheName, keyPrefix, key)) {
      return null;
    }
    try {
      Cache.ValueWrapper valueWrapper = getCache(cacheName).get(getKey(keyPrefix, key));
      Object value = null;
      if (valueWrapper == null) {
        T call = valueLoader.call();
        if (ObjectUtil.isNotEmpty(call)) {
          Field field = ReflectUtil.getField(call.getClass(), HobbitConstant.DB_PRIMARY_KEY);
          if (ObjectUtil.isNotEmpty(field) && ObjectUtil.isEmpty(
              ClassUtil.getMethod(call.getClass(), HobbitConstant.DB_PRIMARY_KEY_METHOD)
                  .invoke(call))) {
            return null;
          }
          getCache(cacheName).put(getKey(keyPrefix, key), call);
          value = call;
        }
      } else {
        value = valueWrapper.get();
      }
      return (T) value;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * 设置缓存
   *
   * @param cacheName 缓存名
   * @param keyPrefix 缓存键前缀
   * @param key       缓存键值
   * @param value     缓存值
   */
  public void put(String cacheName, String keyPrefix, Object key, Object value) {
    getCache(cacheName).put(getKey(keyPrefix, key), value);
  }

  /**
   * 清除缓存
   *
   * @param cacheName 缓存名
   * @param keyPrefix 缓存键前缀
   * @param key       缓存键值
   */
  public void evict(String cacheName, String keyPrefix, Object key) {
    if (Func.hasEmpty(cacheName, keyPrefix, key)) {
      return;
    }
    getCache(cacheName).evict(getKey(keyPrefix, key));
  }

  /**
   * 清空缓存
   *
   * @param cacheName 缓存名
   */
  public void clear(String cacheName) {
    if (Func.isEmpty(cacheName)) {
      return;
    }
    getCache(cacheName).clear();
  }

  private String getKey(String keyPrefix, Object key) {
    return keyPrefix.concat(StringPool.COLON).concat(String.valueOf(key));
  }
}
