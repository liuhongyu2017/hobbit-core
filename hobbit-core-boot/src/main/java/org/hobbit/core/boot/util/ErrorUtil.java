package org.hobbit.core.boot.util;

import org.hobbit.core.log.model.LogError;
import org.hobbit.core.tool.utils.DateUtil;
import org.hobbit.core.tool.utils.Exceptions;
import org.hobbit.core.tool.utils.ObjectUtil;

/**
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
public class ErrorUtil {

  public static void initErrorInfo(Throwable error, LogError event) {
    event.setStackTrace(Exceptions.getStackTraceAsString(error));
    event.setExceptionName(error.getClass().getName());
    event.setMessage(error.getMessage());
    event.setCreateTime(DateUtil.now());
    StackTraceElement[] elements = error.getStackTrace();
    if (ObjectUtil.isNotEmpty(elements)) {
      StackTraceElement element = elements[0];
      event.setMethodClass(element.getClassName());
      event.setFileName(element.getFileName());
      event.setMethodName(element.getMethodName());
      event.setLineNumber(element.getLineNumber());
    }
  }

}
