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

  public ErrorLogEvent(Map<String, Object> source) {
    super(source);
  }
}
