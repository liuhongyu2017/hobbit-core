package org.hobbit.core.cloud.props;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.lang.Nullable;

/**
 * Hystrix Headers 配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/20
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("hobbit.feign.headers")
public class HobbitFeignHeadersProperties {

  /**
   * 用于 聚合层 向调用层传递用户信息 的请求头，默认：x-hobbit-account
   */
  private String account = "X-Hobbit-Account";

  /**
   * RestTemplate 和 Fegin 透传到下层的 Headers 名称表达式
   */
  @Nullable
  private String pattern = "Hobbit*";

  /**
   * RestTemplate 和 Fegin 透传到下层的 Headers 名称列表
   */
  private List<String> allowed = Arrays.asList("X-Real-IP", "x-forwarded-for", "authorization",
      "Hobbit-auth", "Authorization", "Hobbit-Auth");

}
