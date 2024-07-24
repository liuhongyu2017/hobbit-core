package org.hobbit.core.log.exception;

import org.hobbit.core.tool.api.IResultCode;
import org.hobbit.core.tool.api.ResultCode;
import lombok.Getter;

/**
 * Secure 异常
 *
 * @author lhy
 * @version 1.0.0 2023/4/4
 */
@Getter
public class SecureException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private final IResultCode resultCode;

  public SecureException(String message) {
    super(message);
    this.resultCode = ResultCode.UN_AUTHORIZED;
  }

  public SecureException(IResultCode resultCode) {
    super(resultCode.getMessage());
    this.resultCode = resultCode;
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
