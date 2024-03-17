package org.hobbit.core.log.error;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.log.publisher.ErrorLogPublisher;
import org.hobbit.core.tool.api.R;
import org.hobbit.core.tool.api.ResultCode;
import org.hobbit.core.tool.utils.BeanUtil;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

/**
 * 全局异常处理
 *
 * @author lhy
 * @version 1.0.0 2023/4/10
 */
@Slf4j
public class HobbitErrorAttributes extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest,
      ErrorAttributeOptions options) {
    String requestUri = this.getAttr(webRequest, "javax.servlet.error.request_uri");
    Integer status = this.getAttr(webRequest, "javax.servlet.error.status_code");
    Throwable error = getError(webRequest);
    R<?> result;
    if (error == null) {
      log.error("URL:{} error status:{}", requestUri, status);
      result = R.fail(ResultCode.FAILURE, "系统未知异常[HttpStatus]:" + status);
    } else {
      log.error(String.format("URL:%s error status:%d", requestUri, status), error);
      result = R.fail(status == null ? -1 : status, error.getMessage());
    }
    //发送服务异常事件
    ErrorLogPublisher.publishEvent(error, requestUri);
    return BeanUtil.toMap(result);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  private <T> T getAttr(WebRequest webRequest, String name) {
    return (T) webRequest.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
  }
}
