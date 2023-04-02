package org.hobbit.core.context.listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hobbit.core.context.HobbitHttpHeadersGetter;
import org.hobbit.core.context.props.HobbitContextProperties;
import org.hobbit.core.context.props.HobbitContextProperties.Headers;
import org.hobbit.core.tool.constant.HobbitConstant;
import org.hobbit.core.tool.utils.StringUtil;
import org.hobbit.core.tool.utils.ThreadLocalUtil;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;

/**
 * servlet 请求监听
 *
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@RequiredArgsConstructor
public class HobbitServletRequestListener implements ServletRequestListener {

  private final HobbitContextProperties contextProperties;
  private final HobbitHttpHeadersGetter httpHeadersGetter;

  @Override
  public void requestInitialized(ServletRequestEvent event) {
    HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
    Headers headers = contextProperties.getHeaders();
    String requestId = request.getHeader(headers.getRequestId());
    if (StringUtil.isNotBlank(requestId)) {
      MDC.put(HobbitConstant.MDC_REQUEST_ID_KEY, requestId);
    }
    String accountId = request.getHeader(headers.getAccountId());
    if (StringUtil.isNotBlank(accountId)) {
      MDC.put(HobbitConstant.MDC_ACCOUNT_ID_KEY, accountId);
    }
    String tenantId = request.getHeader(headers.getTenantId());
    if (StringUtil.isNotBlank(tenantId)) {
      MDC.put(HobbitConstant.MDC_TENANT_ID_KEY, tenantId);
    }
    // 处理 context，直接传递 request，因为 spring 中的尚未初始化完成
    HttpHeaders httpHeaders = httpHeadersGetter.get(request);
    ThreadLocalUtil.put(HobbitConstant.CONTEXT_KEY, httpHeaders);
  }

  @Override
  public void requestDestroyed(ServletRequestEvent event) {
    // 会话销毁时，清除上下文
    ThreadLocalUtil.clear();
    // 会话销毁时，清除 mdc
    MDC.remove(HobbitConstant.MDC_REQUEST_ID_KEY);
    MDC.remove(HobbitConstant.MDC_ACCOUNT_ID_KEY);
    MDC.remove(HobbitConstant.MDC_TENANT_ID_KEY);
  }
}
