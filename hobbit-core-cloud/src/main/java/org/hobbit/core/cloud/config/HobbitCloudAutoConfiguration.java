package org.hobbit.core.cloud.config;

import feign.RequestInterceptor;
import org.hobbit.core.cloud.feign.HobbitFeignRequestHeaderInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * hobbit cloud 增强配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/20
 */
@AutoConfiguration
public class HobbitCloudAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public RequestInterceptor requestInterceptor() {
    return new HobbitFeignRequestHeaderInterceptor();
  }
}
