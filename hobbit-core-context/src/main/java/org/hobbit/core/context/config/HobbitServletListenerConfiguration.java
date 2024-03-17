package org.hobbit.core.context.config;

import org.hobbit.core.context.HobbitHttpHeadersGetter;
import org.hobbit.core.context.listener.HobbitServletRequestListener;
import org.hobbit.core.context.props.HobbitContextProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = Type.SERVLET)
public class HobbitServletListenerConfiguration {

  @Bean
  public ServletListenerRegistrationBean<?> registerCustomListener(
      HobbitContextProperties properties, HobbitHttpHeadersGetter httpHeadersGetter) {
    return new ServletListenerRegistrationBean<>(
        new HobbitServletRequestListener(properties, httpHeadersGetter));
  }
}
