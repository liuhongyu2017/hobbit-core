package org.hobbit.core.tool.support;

import java.util.function.Supplier;

/**
 * 解决 no binder available 问题
 *
 * @author Chill
 */
public class BinderSupplier implements Supplier<Object> {

  @Override
  public Object get() {
    return null;
  }
}
