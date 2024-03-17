package org.hobbit.core.log.publisher;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.hobbit.core.log.constant.EventConstant;
import org.hobbit.core.log.event.UsualLogEvent;
import org.hobbit.core.log.model.LogUsual;
import org.hobbit.core.log.utils.LogAbstractUtil;
import org.hobbit.core.tool.utils.SpringUtil;
import org.hobbit.core.tool.utils.WebUtil;

/**
 * 日志信息事件发送
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
public class UsualLogPublisher {

  public static void publishEvent(String level, String id, String data) {
    HttpServletRequest request = WebUtil.getRequest();
    LogUsual logUsual = new LogUsual();
    logUsual.setLogLevel(level);
    logUsual.setLogId(id);
    logUsual.setLogData(data);
    Thread thread = Thread.currentThread();
    StackTraceElement[] trace = thread.getStackTrace();
    if (trace.length > 3) {
      logUsual.setMethodClass(trace[3].getClassName());
      logUsual.setMethodName(trace[3].getMethodName());
    }
    LogAbstractUtil.addRequestInfoToLog(request, logUsual);
    Map<String, Object> event = new HashMap<>(16);
    event.put(EventConstant.EVENT_LOG, logUsual);
    SpringUtil.publishEvent(new UsualLogEvent(event));
  }

}
