package org.hobbit.core.cloud.feign;

import feign.Target;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 默认 Fallback，避免写过多fallback类
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
public record HobbitFallbackFactory<T>(
    Target<T> target
) implements FallbackFactory<T> {

  @SuppressWarnings("all")
  @Override
  public T create(Throwable cause) {
    final Class<T> targetType = target.type();
    final String targetName = target.name();
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(targetType);
    enhancer.setUseCache(true);
    enhancer.setCallback(new HobbitFeignFallback<>(targetType, targetName, cause));
    return (T) enhancer.create();
  }
}
