package org.hobbit.core.log.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类
 *
 * @author lhy
 * @version 1.0.0 2023/1/13
 */
@Data
@TableName("hobbit_log_usual")
@EqualsAndHashCode(callSuper = true)
public class LogUsual extends LogAbstract {

  private static final long serialVersionUID = 1L;

  /**
   * 日志级别
   */
  private String logLevel;
  /**
   * 日志业务id
   */
  private String logId;
  /**
   * 日志数据
   */
  private String logData;
}
