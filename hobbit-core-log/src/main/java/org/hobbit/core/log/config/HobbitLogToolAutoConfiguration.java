package org.hobbit.core.log.config;

import lombok.RequiredArgsConstructor;
import org.hobbit.core.launch.props.HobbitProperties;
import org.hobbit.core.launch.server.ServerInfo;
import org.hobbit.core.log.aspect.ApiLogAspect;
import org.hobbit.core.log.event.ApiLogListener;
import org.hobbit.core.log.event.ErrorLogListener;
import org.hobbit.core.log.event.UsualLogListener;
import org.hobbit.core.log.feign.ILogClient;
import org.hobbit.core.log.logger.HobbitLogger;
import org.hobbit.core.log.props.HobbitRequestLogProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 日志工具自动配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/10
 */
@RequiredArgsConstructor
@AutoConfiguration
@ConditionalOnWebApplication
@EnableConfigurationProperties(HobbitRequestLogProperties.class)
public class HobbitLogToolAutoConfiguration {

  private final ILogClient logService;
  private final ServerInfo serverInfo;
  private final HobbitProperties hobbitProperties;

  @Bean
  @ConditionalOnProperty(value = HobbitRequestLogProperties.PREFIX
      + "api.enabled", havingValue = "true", matchIfMissing = true)
  public ApiLogAspect apiLogAspect() {
    return new ApiLogAspect();
  }

  @Bean
  @ConditionalOnProperty(value = HobbitRequestLogProperties.PREFIX
      + "api.enabled", havingValue = "true", matchIfMissing = true)
  public ApiLogListener apiLogListener() {
    return new ApiLogListener(logService, serverInfo, hobbitProperties);
  }

  @Bean
  @ConditionalOnProperty(value = HobbitRequestLogProperties.PREFIX
      + "error.enabled", havingValue = "true", matchIfMissing = true)
  public ErrorLogListener errorEventListener() {
    return new ErrorLogListener(logService, serverInfo, hobbitProperties);
  }

  @Bean
  @ConditionalOnProperty(value = HobbitRequestLogProperties.PREFIX
      + "usual.enabled", havingValue = "true", matchIfMissing = true)
  public UsualLogListener hobbitEventListener() {
    return new UsualLogListener(logService, serverInfo, hobbitProperties);
  }

  @Bean
  @ConditionalOnProperty(value = HobbitRequestLogProperties.PREFIX
      + "usual.enabled", havingValue = "true", matchIfMissing = true)
  public HobbitLogger hobbitLogger() {
    return new HobbitLogger();
  }
}
