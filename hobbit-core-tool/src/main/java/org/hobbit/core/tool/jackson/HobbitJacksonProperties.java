package org.hobbit.core.tool.jackson;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jackson 配置
 *
 * @author L.cm
 */
@Getter
@Setter
@ConfigurationProperties("hobbit.jackson")
public class HobbitJacksonProperties {

  /**
   * null 转为 空，字符串转成""，数组转为[]，对象转为{}，数字转为-1
   */
  private Boolean nullToEmpty = Boolean.TRUE;
  /**
   * 响应到前端，大数值自动写出为 String，避免精度丢失
   */
  private Boolean bigNumToString = Boolean.TRUE;
  /**
   * 支持 MediaType text/plain，用于和 hobbit-api-crypto 一起使用
   */
  private Boolean supportTextPlain = Boolean.FALSE;

}
