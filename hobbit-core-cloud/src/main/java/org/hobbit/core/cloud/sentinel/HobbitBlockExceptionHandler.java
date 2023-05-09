package org.hobbit.core.cloud.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hobbit.core.tool.api.R;
import org.hobbit.core.tool.jackson.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * @author lhy
 * @version 1.0.0 2023/5/9
 */
public class HobbitBlockExceptionHandler implements BlockExceptionHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e)
      throws Exception {
    // 默认情况下返回429（请求过多）。
    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().print(JsonUtil.toJson(R.fail(e.getMessage())));
  }
}
