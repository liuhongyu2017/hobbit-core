package org.hobbit.core.cloud.http;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.hobbit.core.cloud.header.HobbitFeignAccountGetter;
import org.hobbit.core.cloud.header.HobbitHttpHeadersContextHolder;
import org.hobbit.core.cloud.props.HobbitFeignHeadersProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * RestTemplateHeaderInterceptor 传递 Request header
 *
 * @author lhy
 * @version 1.0.0 2023/4/20
 */
@RequiredArgsConstructor
public class RestTemplateHeaderInterceptor implements ClientHttpRequestInterceptor {

  private final HobbitFeignAccountGetter accountGetter;
  private final HobbitFeignHeadersProperties properties;

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] bytes,
      ClientHttpRequestExecution execution) throws IOException {
    HttpHeaders headers = HobbitHttpHeadersContextHolder.get();
    // 考虑2中情况 1. RestTemplate 不是用 hystrix 2. 使用 hystrix
    if (headers == null) {
      headers = HobbitHttpHeadersContextHolder.toHeaders(accountGetter, properties);
    }
    if (headers != null && !headers.isEmpty()) {
      HttpHeaders httpHeaders = request.getHeaders();
      headers.forEach((key, values) -> values.forEach(value -> httpHeaders.add(key, value)));
    }
    return execution.execute(request, bytes);
  }
}
