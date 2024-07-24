package org.hobbit.core.log.exception;

import org.hobbit.core.tool.api.IResultCode;
import org.hobbit.core.tool.api.ResultCode;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author lhy
 * @version 1.0.0 2023/4/10
 */
@Getter
public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private final IResultCode resultCode;

  public ServiceException(String message) {
    super(message);
    this.resultCode = ResultCode.FAILURE;
  }

  public ServiceException(IResultCode resultCode) {
    super(resultCode.getMessage());
    this.resultCode = resultCode;
  }

  public ServiceException(IResultCode resultCode, Throwable cause) {
    super(cause);
    this.resultCode = resultCode;
  }

  /**
   * 提高性能
   *
   * @return Throwable
   */
  @Override
  public Throwable fillInStackTrace() {
    return this;
  }

  public Throwable doFillInStackTrace() {
    return super.fillInStackTrace();
  }

}
