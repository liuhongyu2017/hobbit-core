package org.hobbit.core.tool.beans;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * copy key
 *
 * @author L.cm
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class HobbitBeanCopierKey {

  private final Class<?> source;
  private final Class<?> target;
  private final boolean useConverter;
  private final boolean nonNull;
}
