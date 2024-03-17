package org.hobbit.core.tool.api;

import java.io.Serializable;

/**
 * 业务接口代码
 *
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
public interface IResultCode extends Serializable {

  /**
   * 获取消息
   */
  String getMessage();

  /**
   * 获取状态码
   */
  int getCode();
}
