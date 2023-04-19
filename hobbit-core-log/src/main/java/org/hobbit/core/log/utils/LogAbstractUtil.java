package org.hobbit.core.log.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.hobbit.core.launch.props.HobbitProperties;
import org.hobbit.core.launch.server.ServerInfo;
import org.hobbit.core.log.model.LogAbstract;
import org.hobbit.core.tool.utils.DateUtil;
import org.hobbit.core.tool.utils.ObjectUtil;
import org.hobbit.core.tool.utils.StringPool;
import org.hobbit.core.tool.utils.UrlUtil;
import org.hobbit.core.tool.utils.WebUtil;

/**
 * Log 相关工具
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
public class LogAbstractUtil {

  /**
   * 向log中添加补齐request的信息
   *
   * @param request     请求
   * @param logAbstract 日志基础类
   */
  public static void addRequestInfoToLog(HttpServletRequest request, LogAbstract logAbstract) {
    if (ObjectUtil.isNotEmpty(request)) {
      logAbstract.setRemoteIp(WebUtil.getIP(request));
      logAbstract.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
      logAbstract.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
      logAbstract.setMethod(request.getMethod());
      logAbstract.setParams(WebUtil.getRequestContent(request));
    }
  }

  /**
   * 向log中添加补齐其他的信息（eg：hobbit、server等）
   *
   * @param logAbstract      日志基础类
   * @param hobbitProperties 配置信息
   * @param serverInfo       服务信息
   */
  public static void addOtherInfoToLog(LogAbstract logAbstract, HobbitProperties hobbitProperties,
      ServerInfo serverInfo) {
    logAbstract.setServiceId(hobbitProperties.getName());
    logAbstract.setServerHost(serverInfo.getHostName());
    logAbstract.setServerIp(serverInfo.getIpWithPort());
    logAbstract.setEnv(hobbitProperties.getEnv());
    logAbstract.setCreateTime(DateUtil.now());
    if (logAbstract.getParams() == null) {
      logAbstract.setParams(StringPool.EMPTY);
    }
  }
}
