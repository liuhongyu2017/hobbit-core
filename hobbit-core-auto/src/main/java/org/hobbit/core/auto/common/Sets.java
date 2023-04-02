package org.hobbit.core.auto.common;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
public class Sets {

  /**
   * 不可变 集合
   *
   * @param es  对象
   * @param <E> 泛型
   * @return 集合
   */
  @SafeVarargs
  public static <E> Set<E> ofImmutableSet(E... es) {
    Objects.requireNonNull(es);
    return Stream.of(es).collect(Collectors.toSet());
  }
}
