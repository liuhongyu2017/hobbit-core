package org.hobbit.core.context.config;

import org.hobbit.core.context.HobbitContext;
import org.hobbit.core.context.HobbitHttpHeadersGetter;
import org.hobbit.core.context.HobbitServletContext;
import org.hobbit.core.context.ServletHttpHeadersGetter;
import org.hobbit.core.context.props.HobbitContextProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * hobbit 服务上下文配置
 *
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@AutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(HobbitContextProperties.class)
public class HobbitContextAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public HobbitHttpHeadersGetter hobbitHttpHeadersGetter(
      HobbitContextProperties contextProperties) {
    return new ServletHttpHeadersGetter(contextProperties);
  }

  @Bean
  @ConditionalOnMissingBean
  public HobbitContext hobbitContext(HobbitContextProperties contextProperties,
      HobbitHttpHeadersGetter httpHeadersGetter) {
    return new HobbitServletContext(contextProperties, httpHeadersGetter);
  }
}
