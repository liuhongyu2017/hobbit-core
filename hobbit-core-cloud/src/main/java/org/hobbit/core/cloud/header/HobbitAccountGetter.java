package org.hobbit.core.cloud.header;

import jakarta.servlet.http.HttpServletRequest;
import org.hobbit.core.tool.utils.Charsets;
import org.hobbit.core.tool.utils.UrlUtil;

/**
 * 用户信息获取器
 *
 * @author lhy
 * @version 1.0.0 2023/4/20
 */
public class HobbitAccountGetter implements HobbitFeignAccountGetter {

  @Override
  public String get(HttpServletRequest request) {
    // TODO 这块获取用户信息
    // 增加用户头, 123[admin]
    String xAccount = String.format("%s[%s]", -1, "");
    return UrlUtil.encodeURL(xAccount, Charsets.UTF_8);
  }
}
