package org.hobbit.core.boot.request;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.hobbit.core.boot.props.XssProperties;
import org.hobbit.core.tool.utils.StringPool;

/**
 * Request 全局过滤
 *
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
@RequiredArgsConstructor
public class HobbitRequestFilter implements Filter {

  private final XssProperties xssProperties;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String path = ((HttpServletRequest) request).getServletPath();
    if (!xssProperties.getEnabled() || isSkip(path)) {
      HobbitHttpServletRequestWrapper hobbitRequest =
          new HobbitHttpServletRequestWrapper((HttpServletRequest) request);
      chain.doFilter(hobbitRequest, response);
    } else {
      XssHttpServletRequestWrapper xssRequest =
          new XssHttpServletRequestWrapper((HttpServletRequest) request);
      chain.doFilter(xssRequest, response);
    }
  }

  private boolean isSkip(String path) {
    return xssProperties.getSkipUrl().stream()
        .map(url -> url.replace("/**", StringPool.EMPTY))
        .anyMatch(path::startsWith);
  }
}
