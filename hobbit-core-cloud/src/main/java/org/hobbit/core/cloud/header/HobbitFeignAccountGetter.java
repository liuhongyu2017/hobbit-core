package org.hobbit.core.cloud.header;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;

/**
 * Hobbit 用户信息获取器，用于请求头传递
 *
 * @author lhy
 * @version 1.0.0 2023/4/20
 */
public interface HobbitFeignAccountGetter {

  /**
   * 账号信息获取器
   *
   * @param request HttpServletRequest
   * @return account 信息
   */
  @Nullable
  String get(HttpServletRequest request);
}
