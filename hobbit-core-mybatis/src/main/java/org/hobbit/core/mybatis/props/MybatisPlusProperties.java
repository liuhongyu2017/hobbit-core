package org.hobbit.core.mybatis.props;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lhy
 * @version 1.0.0 2023/5/4
 */
@Data
@ConfigurationProperties(prefix = "hobbit.mybatis-plus")
public class MybatisPlusProperties {

  /**
   * 开启sql日志
   */
  private Boolean sqlLog = true;

  /**
   * sql日志忽略打印关键字
   */
  private List<String> sqlLogExclude = new ArrayList<>();

  /**
   * 分页最大数
   */
  private Long pageLimit = 500L;

  /**
   * 溢出总页数后是否进行处理
   */
  protected Boolean overflow = false;

  /**
   * join优化
   */
  private Boolean optimizeJoin = false;
}
