package org.hobbit.core.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AliasFor;

/**
 * @author lhy
 * @version 1.0.0 2023/4/25
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest
@ExtendWith(HobbitSpringExtension.class)
public @interface HobbitBootTest {

  /**
   * 服务名：appName
   *
   * @return appName
   */
  @AliasFor("appName")
  String value() default "hobbit-test";

  /**
   * 服务名：appName
   *
   * @return appName
   */
  @AliasFor("value")
  String appName() default "hobbit-test";

  /**
   * profile
   *
   * @return profile
   */
  String profile() default "dev";

  /**
   * 启用 ServiceLoader 加载 launcherService
   *
   * @return 是否启用
   */
  boolean enableLoader() default false;
}
