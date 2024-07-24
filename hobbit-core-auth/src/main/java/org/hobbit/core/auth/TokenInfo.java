package org.hobbit.core.auth;

import java.io.Serializable;
import lombok.Data;

/**
 * @author lhy
 * @version 1.0.0 2023/4/4
 */
@Data
public class TokenInfo implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
   * 令牌值
   */
  private String token;
  /**
   * 过期秒数
   */
  private int expire;
}
