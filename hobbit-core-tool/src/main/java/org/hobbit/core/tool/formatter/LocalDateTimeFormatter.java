package org.hobbit.core.tool.formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 * @author lhy
 * @version 1.0.0 2024/3/16
 */
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

  private final String pattern;

  public LocalDateTimeFormatter(String pattern) {
    this.pattern = pattern;
  }

  @Override
  public String print(LocalDateTime object, Locale locale) {
    return object.format(DateTimeFormatter.ofPattern(pattern, locale));
  }

  @Override
  public LocalDateTime parse(String text, Locale locale) {
    return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern, locale));
  }
}
