package org.hobbit.core.redis.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lhy
 * @version 1.0.0 2023/4/24
 */
@Getter
@Setter
@ConfigurationProperties("hobbit.rate-limiter")
public class HobbitRateLimiterProperties {

  /**
   * 是否开启：默认为：false。
   */
  private Boolean enabled = Boolean.FALSE;
}
