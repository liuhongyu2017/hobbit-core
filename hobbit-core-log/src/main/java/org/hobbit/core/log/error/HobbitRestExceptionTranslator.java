package org.hobbit.core.log.error;

import jakarta.servlet.Servlet;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.log.exception.SecureException;
import org.hobbit.core.log.exception.ServiceException;
import org.hobbit.core.log.props.HobbitRequestLogProperties;
import org.hobbit.core.log.publisher.ErrorLogPublisher;
import org.hobbit.core.tool.api.R;
import org.hobbit.core.tool.api.ResultCode;
import org.hobbit.core.tool.utils.Func;
import org.hobbit.core.tool.utils.UrlUtil;
import org.hobbit.core.tool.utils.WebUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 未知异常转移和发送，方便监听，对未知异常统一处理。
 *
 * @author lhy
 * @version 1.0.0 2023/4/10
 */
@Slf4j
@Order
@RequiredArgsConstructor
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@RestControllerAdvice
public class HobbitRestExceptionTranslator {

  private final HobbitRequestLogProperties properties;

  @ExceptionHandler(ServiceException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public R<?> handleError(ServiceException e) {
    log.error("业务异常", e);
    if (properties.getErrorLog()) {
      //发送服务异常事件
      ErrorLogPublisher.publishEvent(e, UrlUtil.getPath(
          Objects.requireNonNull(WebUtil.getRequest()).getRequestURI()));
    }
    return R.fail(e.getResultCode(), e.getMessage());
  }

  @ExceptionHandler(SecureException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public R<?> handleError(SecureException e) {
    log.error("认证异常", e);
    if (properties.getErrorLog()) {
      //发送服务异常事件
      ErrorLogPublisher.publishEvent(e, UrlUtil.getPath(
          Objects.requireNonNull(WebUtil.getRequest()).getRequestURI()));
    }
    return R.fail(e.getResultCode(), e.getMessage());
  }

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public R<?> handleError(Throwable e) {
    log.error("服务器异常", e);
    if (properties.getErrorLog()) {
      //发送服务异常事件
      ErrorLogPublisher.publishEvent(e, UrlUtil.getPath(
          Objects.requireNonNull(WebUtil.getRequest()).getRequestURI()));
    }
    return R.fail(ResultCode.INTERNAL_SERVER_ERROR,
        (Func.isEmpty(e.getMessage()) ? ResultCode.INTERNAL_SERVER_ERROR.getMessage()
            : e.getMessage()));
  }
}
