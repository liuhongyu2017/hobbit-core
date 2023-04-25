package org.hobbit.core.tool.jackson.desensitization;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hobbit.core.tool.jackson.Symbol;

/**
 * 手机号脱敏
 *
 * @author lhy
 * @version 1.0.0 2022/3/30 12:00
 */
public class PhoneDesensitization implements StringDesensitization {

  /**
   * 手机号正则
   */
  private static final Pattern DEFAULT_PATTERN =
      Pattern.compile("(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}");

  @Override
  public String desensitize(String target) {
    Matcher matcher = DEFAULT_PATTERN.matcher(target);
    while (matcher.find()) {
      String group = matcher.group();
      target = target.replace(group,
          group.substring(0, 3) + Symbol.getSymbol(4, Symbol.STAR) + group.substring(7, 11));
    }
    return target;
  }
}
