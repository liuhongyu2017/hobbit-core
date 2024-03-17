package org.hobbit.core.boot.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.hobbit.core.boot.props.XssProperties;
import org.hobbit.core.boot.request.HobbitRequestFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

/**
 * 过滤器配置类
 *
 * @author lhy
 * @version 1.0.0 2023/1/13
 */
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(XssProperties.class)
public class RequestConfiguration {

  private final XssProperties xssProperties;

  /**
   * 全局过滤器
   */
  @Bean
  public FilterRegistrationBean<HobbitRequestFilter> hobbitFilterRegistration() {
    FilterRegistrationBean<HobbitRequestFilter> registration = new FilterRegistrationBean<>();
    registration.setDispatcherTypes(DispatcherType.REQUEST);
    registration.setFilter(new HobbitRequestFilter(xssProperties));
    registration.addUrlPatterns("/*");
    registration.setName("hobbitRequestFilter");
    registration.setOrder(Ordered.LOWEST_PRECEDENCE);
    return registration;
  }
}
