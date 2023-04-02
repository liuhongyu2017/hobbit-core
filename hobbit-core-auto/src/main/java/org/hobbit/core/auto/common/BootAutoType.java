package org.hobbit.core.auto.common;

import org.hobbit.core.auto.annotation.AutoContextInitializer;
import org.hobbit.core.auto.annotation.AutoEnvPostProcessor;
import org.hobbit.core.auto.annotation.AutoFailureAnalyzer;
import org.hobbit.core.auto.annotation.AutoListener;
import org.hobbit.core.auto.annotation.AutoRunListener;

/**
 * 注解类型
 *
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
public enum BootAutoType {
  /**
   * Component，组合注解，添加到 spring.factories
   */
  COMPONENT("org.springframework.stereotype.Component",
      "org.springframework.boot.autoconfigure.EnableAutoConfiguration"),
  /**
   * ApplicationContextInitializer 添加到 spring.factories
   */
  CONTEXT_INITIALIZER(AutoContextInitializer.class.getName(),
      "org.springframework.context.ApplicationContextInitializer"),
  /**
   * ApplicationListener 添加到 spring.factories
   */
  LISTENER(AutoListener.class.getName(), "org.springframework.context.ApplicationListener"),
  /**
   * SpringApplicationRunListener 添加到 spring.factories
   */
  RUN_LISTENER(AutoRunListener.class.getName(),
      "org.springframework.boot.SpringApplicationRunListener"),
  /**
   * FailureAnalyzer 添加到 spring.factories
   */
  FAILURE_ANALYZER(AutoFailureAnalyzer.class.getName(),
      "org.springframework.boot.diagnostics.FailureAnalyzer"),
  /**
   * EnvironmentPostProcessor 添加到 spring.factories
   */
  ENV_POST_PROCESSOR(AutoEnvPostProcessor.class.getName(),
      "org.springframework.boot.env.EnvironmentPostProcessor");

  private final String annotationName;
  private final String configureKey;

  BootAutoType(String annotationName, String configureKey) {
    this.annotationName = annotationName;
    this.configureKey = configureKey;
  }

  public final String getAnnotationName() {
    return annotationName;
  }

  public final String getConfigureKey() {
    return configureKey;
  }

}
