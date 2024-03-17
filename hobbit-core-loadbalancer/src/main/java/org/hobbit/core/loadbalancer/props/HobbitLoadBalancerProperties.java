package org.hobbit.core.loadbalancer.props;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * LoadBalancer 配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(HobbitLoadBalancerProperties.PROPERTIES_PREFIX)
public class HobbitLoadBalancerProperties {

  public static final String PROPERTIES_PREFIX = "hobbit.loadbalancer";

  /**
   * 是否开启自定义负载均衡
   */
  private boolean enabled = true;
  /**
   * 优先的ip列表，支持通配符，例如：10.20.0.8*、10.20.0.*
   */
  private List<String> priorIpPattern = new ArrayList<>();
}
