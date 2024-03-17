package org.hobbit.core.log.publisher;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.hobbit.core.log.constant.EventConstant;
import org.hobbit.core.log.event.ErrorLogEvent;
import org.hobbit.core.log.model.LogError;
import org.hobbit.core.log.utils.LogAbstractUtil;
import org.hobbit.core.tool.utils.Exceptions;
import org.hobbit.core.tool.utils.Func;
import org.hobbit.core.tool.utils.SpringUtil;
import org.hobbit.core.tool.utils.WebUtil;

/**
 * 异常信息事件发送
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
public class ErrorLogPublisher {

  public static void publishEvent(Throwable error, String requestUri) {
    HttpServletRequest request = WebUtil.getRequest();
    LogError logError = new LogError();
    logError.setRequestUri(requestUri);
    if (Func.isNotEmpty(error)) {
      logError.setStackTrace(Exceptions.getStackTraceAsString(error));
      logError.setExceptionName(error.getClass().getName());
      logError.setMessage(error.getMessage());
      StackTraceElement[] elements = error.getStackTrace();
      if (Func.isNotEmpty(elements)) {
        StackTraceElement element = elements[0];
        logError.setMethodName(element.getMethodName());
        logError.setMethodClass(element.getClassName());
        logError.setFileName(element.getFileName());
        logError.setLineNumber(element.getLineNumber());
      }
    }
    LogAbstractUtil.addRequestInfoToLog(request, logError);
    Map<String, Object> event = new HashMap<>(16);
    event.put(EventConstant.EVENT_LOG, logError);
    SpringUtil.publishEvent(new ErrorLogEvent(event));
  }
}
