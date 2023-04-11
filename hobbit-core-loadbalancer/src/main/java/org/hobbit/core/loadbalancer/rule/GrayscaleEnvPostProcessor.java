package org.hobbit.core.loadbalancer.rule;

import net.dreamlu.mica.auto.annotation.AutoEnvPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

/**
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
@AutoEnvPostProcessor
public class GrayscaleEnvPostProcessor implements EnvironmentPostProcessor, Ordered {

  private static final String GREYSCALE_KEY = "blade.loadbalancer.version";
  private static final String METADATA_KEY = "spring.cloud.nacos.discovery.metadata.version";

  @Override
  public void postProcessEnvironment(
      ConfigurableEnvironment environment, SpringApplication application) {
    String version = environment.getProperty(GREYSCALE_KEY);

    if (StringUtils.hasText(version)) {
      environment.getSystemProperties().put(METADATA_KEY, version);
    }
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }
}
