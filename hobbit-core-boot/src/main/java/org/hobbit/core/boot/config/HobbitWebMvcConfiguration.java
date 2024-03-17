package org.hobbit.core.boot.config;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.boot.props.HobbitFileProperties;
import org.hobbit.core.boot.props.HobbitUploadProperties;
import org.hobbit.core.boot.resolver.TokenArgumentResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lhy
 * @version 1.0.0 2023/1/13
 */
@Slf4j
@AutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
@EnableConfigurationProperties({HobbitFileProperties.class, HobbitUploadProperties.class})
public class HobbitWebMvcConfiguration implements WebMvcConfigurer {

  /**
   * Controller 参数解析
   */
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new TokenArgumentResolver());
  }
}
