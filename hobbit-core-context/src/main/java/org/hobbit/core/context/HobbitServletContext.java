package org.hobbit.core.context;

import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.hobbit.core.context.props.HobbitContextProperties;
import org.hobbit.core.tool.constant.HobbitConstant;
import org.hobbit.core.tool.utils.StringUtil;
import org.hobbit.core.tool.utils.ThreadLocalUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.http.HttpHeaders;

/**
 * hobbit servlet 上下文
 *
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = Type.SERVLET)
public class HobbitServletContext implements HobbitContext {

  private final HobbitContextProperties contextProperties;
  private final HobbitHttpHeadersGetter httpHeadersGetter;

  @Override
  public String getRequestId() {
    return get(contextProperties.getHeaders().getRequestId());
  }

  @Override
  public String getAccountId() {
    return get(contextProperties.getHeaders().getAccountId());
  }

  @Override
  public String getTenantId() {
    return get(contextProperties.getHeaders().getTenantId());
  }

  @Override
  public String get(String ctxKey) {
    HttpHeaders headers = ThreadLocalUtil.getIfAbsent(HobbitConstant.CONTEXT_KEY,
        httpHeadersGetter::get);
    if (headers == null || headers.isEmpty()) {
      return null;
    }
    return headers.getFirst(ctxKey);
  }

  @Override
  public <T> T get(String ctxKey, Function<String, T> function) {
    String ctxValue = get(ctxKey);
    if (StringUtil.isBlank(ctxValue)) {
      return null;
    }
    return function.apply(ctxKey);
  }
}
