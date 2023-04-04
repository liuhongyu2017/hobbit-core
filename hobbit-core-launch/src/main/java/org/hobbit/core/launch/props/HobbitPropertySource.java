package org.hobbit.core.launch.props;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.Ordered;

/**
 * 自定义资源文件读取，优先级最低
 *
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HobbitPropertySource {

  /**
   * Indicate the resource location(s) of the properties file to be loaded. for example, {@code
   * "classpath:/com/example/app.yml"}
   *
   * @return location(s)
   */
  String value();

  /**
   * load app-{activeProfile}.yml
   *
   * @return {boolean}
   */
  boolean loadActiveProfile() default true;

  /**
   * Get the order value of this resource.
   *
   * @return order
   */
  int order() default Ordered.LOWEST_PRECEDENCE;
}
