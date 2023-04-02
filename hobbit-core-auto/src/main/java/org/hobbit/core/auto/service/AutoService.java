package org.hobbit.core.auto.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AutoService {

  /**
   * 返回此服务提供程序实现的接口。
   *
   * @return interface array
   */
  Class<?>[] value();
}
