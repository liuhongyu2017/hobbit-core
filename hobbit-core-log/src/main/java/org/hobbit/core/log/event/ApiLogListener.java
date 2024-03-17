package org.hobbit.core.log.event;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.launch.props.HobbitProperties;
import org.hobbit.core.launch.server.ServerInfo;
import org.hobbit.core.log.constant.EventConstant;
import org.hobbit.core.log.feign.ILogClient;
import org.hobbit.core.log.model.LogApi;
import org.hobbit.core.log.utils.LogAbstractUtil;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * 异步监听日志事件
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
@Slf4j
@RequiredArgsConstructor
public class ApiLogListener {

  private final ILogClient logService;
  private final ServerInfo serverInfo;
  private final HobbitProperties hobbitProperties;

  @SuppressWarnings("unchecked")
  @Async
  @Order
  @EventListener(ApiLogEvent.class)
  public void saveApiLog(ApiLogEvent event) {
    Map<String, Object> source = (Map<String, Object>) event.getSource();
    LogApi logApi = (LogApi) source.get(EventConstant.EVENT_LOG);
    LogAbstractUtil.addOtherInfoToLog(logApi, hobbitProperties, serverInfo);
    logService.saveApiLog(logApi);
  }
}
