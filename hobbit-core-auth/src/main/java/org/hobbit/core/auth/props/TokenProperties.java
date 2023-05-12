package org.hobbit.core.auth.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jwt 配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
@Data
@ConfigurationProperties("hobbit.token")
public class TokenProperties {

  /**
   * 是否只可同时在线一人
   */
  private Boolean single = Boolean.FALSE;

}
