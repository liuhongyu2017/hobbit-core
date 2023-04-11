package org.hobbit.core.log.publisher;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.hobbit.core.log.annotation.ApiLog;
import org.hobbit.core.log.constant.EventConstant;
import org.hobbit.core.log.event.ApiLogEvent;
import org.hobbit.core.log.model.LogApi;
import org.hobbit.core.log.utils.LogAbstractUtil;
import org.hobbit.core.tool.constant.HobbitConstant;
import org.hobbit.core.tool.utils.SpringUtil;
import org.hobbit.core.tool.utils.WebUtil;

/**
 * API日志信息事件发送
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
public class ApiLogPublisher {

  public static void publishEvent(String methodName, String methodClass, ApiLog apiLog, long time) {
    HttpServletRequest request = WebUtil.getRequest();
    LogApi logApi = new LogApi();
    logApi.setType(HobbitConstant.LOG_NORMAL_TYPE);
    logApi.setTitle(apiLog.value());
    logApi.setTime(String.valueOf(time));
    logApi.setMethodClass(methodClass);
    logApi.setMethodName(methodName);
    LogAbstractUtil.addRequestInfoToLog(request, logApi);
    Map<String, Object> event = new HashMap<>(16);
    event.put(EventConstant.EVENT_LOG, logApi);
    SpringUtil.publishEvent(new ApiLogEvent(event));
  }

}
