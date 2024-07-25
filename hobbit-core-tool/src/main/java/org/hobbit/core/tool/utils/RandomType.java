package org.hobbit.core.tool.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 生成的随机数类型
 *
 * @author lhy
 * @version 1.0.0 2024/07/24
 */
@Getter
@RequiredArgsConstructor
public enum RandomType {
  /**
   * Int
   */
  INT(RandomType.INT_STR),
  /**
   * String
   */
  STRING(RandomType.STR_STR),
  /**
   * All
   */
  ALL(RandomType.ALL_STR);

  private final String factor;

  /**
   * 随机字符串因子
   */
  private static final String INT_STR = "0123456789";
  private static final String STR_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private static final String ALL_STR = INT_STR + STR_STR;
}
