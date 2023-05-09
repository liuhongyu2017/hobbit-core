package org.hobbit.core.cloud.config;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import feign.RequestInterceptor;
import lombok.AllArgsConstructor;
import org.hobbit.core.cloud.feign.HobbitFeignRequestHeaderInterceptor;
import org.hobbit.core.cloud.sentinel.HobbitBlockExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * Sentinel 配置类
 *
 * @author lhy
 * @version 1.0.0 2023/5/9
 */
@AllArgsConstructor
@AutoConfiguration(before = SentinelFeignAutoConfiguration.class)
@ConditionalOnProperty(name = "feign.sentinel.enabled")
public class HobbitSentinelAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public BlockExceptionHandler blockExceptionHandler() {
    return new HobbitBlockExceptionHandler();
  }

  @Bean
  @ConditionalOnMissingBean
  public RequestInterceptor requestInterceptor() {
    return new HobbitFeignRequestHeaderInterceptor();
  }
}
