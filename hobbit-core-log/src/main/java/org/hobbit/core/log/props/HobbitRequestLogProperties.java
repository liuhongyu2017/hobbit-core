package org.hobbit.core.log.props;

import lombok.Getter;
import lombok.Setter;
import org.hobbit.core.launch.log.HobbitLogLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lhy
 * @version 1.0.0 2023/4/10
 */
@Getter
@Setter
@ConfigurationProperties(HobbitLogLevel.REQ_LOG_PROPS_PREFIX)
public class HobbitRequestLogProperties {

  /**
   * 前缀
   */
  public static final String PREFIX = "hobbit.log";
  /**
   * 是否开启请求日志
   */
  private Boolean enabled = true;

  /**
   * 是否开启异常日志推送
   */
  private Boolean errorLog = true;

  /**
   * 日志级别配置，默认：BODY
   */
  private HobbitLogLevel level = HobbitLogLevel.BODY;
}
