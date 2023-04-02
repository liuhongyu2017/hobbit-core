package org.hobbit.core.tool.beans;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * copy 字段 配置
 *
 * @author L.cm
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CopyProperty {

  /**
   * 属性名，用于指定别名，默认使用：field name
   *
   * @return 属性名
   */
  String value() default "";

  /**
   * 忽略：默认为 false
   *
   * @return 是否忽略
   */
  boolean ignore() default false;
}
