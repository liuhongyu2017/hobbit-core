package org.hobbit.core.tool.jackson;

/**
 * @author lhy
 * @version 1.0.0 2022/3/30 10:38
 */
public class Symbol {

  /**
   * 脱敏符号
   */
  public static final String STAR = "*";

  private Symbol() {

  }

  /**
   * 获取符号
   *
   * @param number 符号个数
   * @param symbol 符号
   */
  public static String getSymbol(int number, String symbol) {
    return String.valueOf(symbol).repeat(Math.max(0, number));
  }
}
