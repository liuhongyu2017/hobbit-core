package org.hobbit.core.tool.function;

import org.springframework.lang.Nullable;

/**
 * 受检的 Supplier
 *
 * @author L.cm
 */
@FunctionalInterface
public interface CheckedSupplier<T> {

  /**
   * Run the Supplier
   *
   * @return T
   */
  @Nullable
  T get();

}
