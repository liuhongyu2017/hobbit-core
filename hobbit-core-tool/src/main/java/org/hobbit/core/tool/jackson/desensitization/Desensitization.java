package org.hobbit.core.tool.jackson.desensitization;

/**
 * 顶级脱敏实现
 *
 * @author lhy
 * @version 1.0.0 2022/3/30 10:23
 */
public interface Desensitization<T> {

  /**
   * 脱敏实现
   *
   * @param target 脱敏对象
   * @return 返回结果
   */
  T desensitize(T target);
}
