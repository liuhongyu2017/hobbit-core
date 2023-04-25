package org.hobbit.core.tool.jackson.desensitization;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lhy
 * @version 1.0.0 2022/3/30 10:35
 */
public class DesensitizationFactory {

  private DesensitizationFactory() {
  }

  private static final Map<Class<?>, Desensitization<?>> map = new HashMap<>();

  public static Desensitization<?> getDesensitization(Class<?> clazz) {
    if (clazz.isInterface()) {
      throw new UnsupportedOperationException("这是一个接口，期望是一个实现类！");
    }
    // 对脱敏实现类进行缓存
    return map.computeIfAbsent(clazz, k -> {
      try {
        return (Desensitization<?>) clazz.getDeclaredConstructor().newInstance();
      } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
        throw new UnsupportedOperationException(e.getMessage(), e);
      }
    });
  }
}
