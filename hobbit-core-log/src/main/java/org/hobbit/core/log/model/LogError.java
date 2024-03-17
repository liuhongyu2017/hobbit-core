package org.hobbit.core.log.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lhy
 * @version 1.0.0 2023/1/13
 */
@Data
@TableName("hobbit_log_error")
@EqualsAndHashCode(callSuper = true)
public class LogError extends LogAbstract {

  /**
   * 堆栈信息
   */
  private String stackTrace;
  /**
   * 异常名
   */
  private String exceptionName;
  /**
   * 异常消息
   */
  private String message;

  /**
   * 文件名
   */
  private String fileName;

  /**
   * 代码行数
   */
  private Integer lineNumber;
}
