package org.hobbit.core.jwt.constant;

/**
 * Jwt 常量
 *
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
public interface JwtConstant {

  /**
   * 默认key
   */
  String DEFAULT_SECRET_KEY = "sidfoiasdhoaifiowevnjvnaoiowpeirasdnfascasdfopwejpogankasdjfwejpodoasdalsdkm";

  /**
   * key安全长度，具体见：https://tools.ietf.org/html/rfc7518#section-3.2
   */
  int SECRET_KEY_LENGTH = 32;

}
