package org.hobbit.core.secure.config;

import lombok.RequiredArgsConstructor;
import org.hobbit.core.secure.handler.HobbitPermissionHandler;
import org.hobbit.core.secure.handler.IPermissionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * secure注册默认配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/22
 */
@Order
@Configuration
@RequiredArgsConstructor
public class RegistryConfiguration {

  private final JdbcTemplate jdbcTemplate;

  @Bean
  @ConditionalOnMissingBean(IPermissionHandler.class)
  public IPermissionHandler permissionHandler() {
    return new HobbitPermissionHandler(jdbcTemplate);
  }
}
