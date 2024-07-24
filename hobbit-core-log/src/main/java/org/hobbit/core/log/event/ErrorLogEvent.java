package org.hobbit.core.log.event;

import java.util.Map;
import org.springframework.context.ApplicationEvent;

/**
 * 错误日志事件
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
public class ErrorLogEvent extends ApplicationEvent {

  private static final long serialVersionUID = 1L;

  public ErrorLogEvent(Map<String, Object> source) {
    super(source);
  }
}
