package org.hobbit.core.context;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hobbit.core.context.props.HobbitContextProperties;
import org.hobbit.core.tool.utils.StringUtil;
import org.hobbit.core.tool.utils.WebUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpHeaders;

/**
 * HttpHeaders 获取器
 *
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ServletHttpHeadersGetter implements HobbitHttpHeadersGetter {

  private final HobbitContextProperties properties;

  @Override
  public HttpHeaders get() {
    HttpServletRequest request = WebUtil.getRequest();
    if (request == null) {
      return null;
    }
    return get(request);
  }

  @Override
  public HttpHeaders get(HttpServletRequest request) {
    org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
    List<String> crossHeaders = properties.getCrossHeaders();
    // 传递请求头
    Enumeration<String> headerNames = request.getHeaderNames();
    if (headerNames != null) {
      List<String> allowed = properties.getHeaders().getAllowed();
      while (headerNames.hasMoreElements()) {
        String key = headerNames.nextElement();
        // 只支持配置的 header
        if (crossHeaders.contains(key) || allowed.contains(key)) {
          String values = request.getHeader(key);
          // header value 不为空的 传递
          if (StringUtil.isNotBlank(values)) {
            headers.add(key, values);
          }
        }
      }
    }
    return headers;
  }
}
