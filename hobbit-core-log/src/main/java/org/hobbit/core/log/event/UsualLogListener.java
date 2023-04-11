package org.hobbit.core.log.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
@Slf4j
@RequiredArgsConstructor
public class UsualLogListener {

  @Async
  @Order
  @EventListener(UsualLogEvent.class)
  public void saveUsualLog(UsualLogEvent event) {

  }
}
