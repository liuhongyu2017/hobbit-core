package org.hobbit.core.tool.function;

import org.springframework.lang.Nullable;

/**
 * 受检的 Consumer
 *
 * @author L.cm
 */
@FunctionalInterface
public interface CheckedConsumer<T> {

  /**
   * Run the Consumer
   *
   * @param t T
   */
  void accept(@Nullable T t);

}
