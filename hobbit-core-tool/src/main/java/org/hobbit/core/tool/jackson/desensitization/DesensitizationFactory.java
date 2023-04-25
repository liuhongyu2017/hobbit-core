package org.hobbit.core.tool.jackson.desensitization;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import lombok.SneakyThrows;

/**
 * @author lhy
 * @version 1.0.0 2022/3/30 10:35
 */
public class DesensitizationFactory {

  private DesensitizationFactory() {
  }

  private static final Cache<Class<?>, Desensitization<?>> cache = CacheBuilder.newBuilder()
      // 设置并发级别为cpu核心数
      .concurrencyLevel(Runtime.getRuntime().availableProcessors()).build();

  @SneakyThrows(ExecutionException.class)
  public static Desensitization<?> getDesensitization(Class<?> clazz) {
    if (clazz.isInterface()) {
      throw new UnsupportedOperationException("这是一个接口，期望是一个实现类！");
    }
    // 对脱敏实现类进行缓存
    return cache.get(clazz, () -> {
      try {
        return (Desensitization<?>) clazz.getDeclaredConstructor().newInstance();
      } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
        throw new UnsupportedOperationException(e.getMessage(), e);
      }
    });
  }
}
