package org.hobbit.core.context.props;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hobbit.core.tool.constant.TokenConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@Getter
@Setter
@ConfigurationProperties(HobbitContextProperties.PREFIX)
public class HobbitContextProperties {

  /**
   * 配置前缀
   */
  public static final String PREFIX = "hobbit.context";
  /**
   * 上下文传递的 headers 信息
   */
  private Headers headers = new Headers();

  @Getter
  @Setter
  public static class Headers {

    /**
     * 请求id，默认：Hobbit-RequestId
     */
    private String requestId = "Hobbit-RequestId";
    /**
     * 用于 聚合层 向调用层传递用户信息 的请求头，默认：Hobbit-AccountId
     */
    private String accountId = "Hobbit-AccountId";
    /**
     * 用于 聚合层 向调用层传递租户id 的请求头，默认：Hobbit-TenantId
     */
    private String tenantId = "Hobbit-TenantId";
    /**
     * 自定义 RestTemplate 和 Feign 透传到下层的 Headers 名称列表
     */
    private List<String> allowed = Arrays.asList("X-Real-IP", "x-forwarded-for", "version",
        "VERSION", "authorization", "Authorization", TokenConstant.HEADER.toLowerCase(),
        TokenConstant.HEADER);
  }

  /**
   * 获取跨服务的请求头
   *
   * @return 请求头列表
   */
  public List<String> getCrossHeaders() {
    List<String> headerList = new ArrayList<>();
    headerList.add(headers.getRequestId());
    headerList.add(headers.getAccountId());
    headerList.add(headers.getTenantId());
    headerList.addAll(headers.getAllowed());
    return headerList;
  }
}
