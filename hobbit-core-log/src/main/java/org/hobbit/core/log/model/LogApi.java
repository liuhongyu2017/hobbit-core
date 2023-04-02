package org.hobbit.core.log.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lhy
 * @version 1.0.0 2023/1/13
 */
@Data
@TableName("hobbit_log_api")
@EqualsAndHashCode(callSuper = true)
public class LogApi extends LogAbstract {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 日志类型
   */
  private String type;
  /**
   * 日志标题
   */
  private String title;
  /**
   * 执行时间
   */
  private String time;
}
