package org.hobbit.core.launch.config;

import org.hobbit.core.launch.props.HobbitProperties;
import org.hobbit.core.launch.props.HobbitPropertySourcePostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@AutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(HobbitProperties.class)
public class HobbitPropertyConfiguration {

  @Bean
  public HobbitPropertySourcePostProcessor hobbitPropertySourcePostProcessor() {
    return new HobbitPropertySourcePostProcessor();
  }
}
