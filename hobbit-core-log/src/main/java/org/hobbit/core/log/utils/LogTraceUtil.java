package org.hobbit.core.log.utils;

import org.hobbit.core.tool.utils.StringUtil;
import org.slf4j.MDC;

/**
 * 日志追踪工具类
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
public class LogTraceUtil {

  private static final String UNIQUE_ID = "traceId";

  /**
   * 获取日志追踪id格式
   */
  public static String getTraceId() {
    return StringUtil.randomUUID();
  }

  /**
   * 插入traceId
   */
  public static void insert() {
    MDC.put(UNIQUE_ID, getTraceId());
  }

  /**
   * 移除traceId
   */
  public static void remove() {
    MDC.remove(UNIQUE_ID);
  }
}
