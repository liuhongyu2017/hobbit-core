package org.hobbit.core.log.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * 异步监听错误日志事件
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
@Slf4j
@RequiredArgsConstructor
public class ErrorLogListener {

  @Async
  @Order
  @EventListener(ErrorLogEvent.class)
  public void saveErrorLog(ErrorLogEvent event) {

  }
}
