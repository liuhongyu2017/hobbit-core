package org.hobbit.core.cloud.config;

import feign.Logger;
import feign.Logger.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.cloud.http.RestTemplateHeaderInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @author lhy
 * @version 1.0.0 2023/5/9
 */
@Slf4j
@RequiredArgsConstructor
@AutoConfiguration
@ConditionalOnProperty(value = "hobbit.http.enabled", matchIfMissing = true)
public class RestTemplateConfiguration {

  @Bean
  public RestTemplateHeaderInterceptor requestHeaderInterceptor() {
    return new RestTemplateHeaderInterceptor();
  }

  /**
   * NONE，无日志记录（默认）。
   * <p>
   * BASIC，仅记录请求方法和 URL 以及响应状态代码和执行时间。
   * <p>
   * HEADERS，记录基本信息以及请求和响应标头。
   * <p>
   * FULL，记录请求和响应的标头、正文和元数据。
   */
  @Bean
  public Logger.Level feignLoggerLevel() {
    return Level.FULL;
  }
}
