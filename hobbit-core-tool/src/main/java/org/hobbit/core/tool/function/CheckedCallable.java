package org.hobbit.core.tool.function;

import org.springframework.lang.Nullable;

/**
 * 受检的 Callable
 *
 * @author L.cm
 */
@FunctionalInterface
public interface CheckedCallable<T> {

  /**
   * Run this callable.
   *
   * @return result
   */
  @Nullable
  T call();
}
