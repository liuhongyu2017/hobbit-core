package org.hobbit.core.cloud.http;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.hobbit.core.tool.constant.HobbitConstant;
import org.hobbit.core.tool.utils.ThreadLocalUtil;
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
@SuppressWarnings("NullableProblems")
@RequiredArgsConstructor
public class RestTemplateHeaderInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] bytes,
      ClientHttpRequestExecution execution) throws IOException {
    HttpHeaders headers = ThreadLocalUtil.get(HobbitConstant.CONTEXT_KEY);
    if (headers != null && !headers.isEmpty()) {
      HttpHeaders httpHeaders = request.getHeaders();
      headers.forEach((key, values) -> values.forEach(value -> httpHeaders.add(key, value)));
    }
    return execution.execute(request, bytes);
  }
}
