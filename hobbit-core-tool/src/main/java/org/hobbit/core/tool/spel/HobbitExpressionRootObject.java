package org.hobbit.core.tool.spel;

import java.lang.reflect.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ExpressionRootObject
 *
 * @author L.cm
 */
@Getter
@AllArgsConstructor
public class HobbitExpressionRootObject {

  private final Method method;

  private final Object[] args;

  private final Object target;

  private final Class<?> targetClass;

  private final Method targetMethod;
}
