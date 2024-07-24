package org.hobbit.core.log.config;

import org.hobbit.core.log.error.HobbitErrorAttributes;
import org.hobbit.core.log.error.HobbitErrorController;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import jakarta.servlet.Servlet;
import lombok.RequiredArgsConstructor;

/**
 * 统一异常处理
 *
 * @author lhy
 * @version 1.0.0 2023/4/10
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication
@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
public class HobbitErrorMvcAutoConfiguration {

  private final ServerProperties serverProperties;

  @Bean
  @ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchStrategy.CURRENT)
  public DefaultErrorAttributes errorAttributes() {
    return new HobbitErrorAttributes();
  }

  @Bean
  @ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
  public BasicErrorController basicErrorController(ErrorAttributes errorAttributes) {
    return new HobbitErrorController(errorAttributes, serverProperties.getError());
  }
}
