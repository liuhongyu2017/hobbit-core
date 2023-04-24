package org.hobbit.core.redis.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lhy
 * @version 1.0.0 2023/4/23
 */
@Getter
@Setter
@ConfigurationProperties("hobbit.redis")
public class HobbitRedisProperties {

  /**
   * 序列化方式
   */
  private SerializerType serializerType = SerializerType.ProtoStuff;

  public enum SerializerType {
    /**
     * 默认:ProtoStuff 序列化
     */
    ProtoStuff,
    /**
     * json 序列化
     */
    JSON,
    /**
     * jdk 序列化
     */
    JDK
  }
}
