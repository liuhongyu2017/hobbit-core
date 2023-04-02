package org.hobbit.core.context;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

/**
 * HttpHeaders 获取器，用于跨域和线程传递
 *
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
public interface HobbitHttpHeadersGetter {

  /**
   * 获取 HttpHeaders
   */
  @Nullable
  HttpHeaders get();

  /**
   * 获取 HttpHeaders
   *
   * @param request 请求
   */
  @Nullable
  HttpHeaders get(HttpServletRequest request);
}
