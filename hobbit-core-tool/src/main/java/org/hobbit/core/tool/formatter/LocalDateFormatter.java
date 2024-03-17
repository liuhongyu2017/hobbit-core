package org.hobbit.core.tool.formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 * @author lhy
 * @version 1.0.0 2024/3/16
 */
public class LocalDateFormatter implements Formatter<LocalDate> {

  private final String pattern;

  public LocalDateFormatter(String pattern) {
    this.pattern = pattern;
  }

  @Override
  public String print(LocalDate object, Locale locale) {
    return object.format(DateTimeFormatter.ofPattern(pattern, locale));
  }

  @Override
  public LocalDate parse(String text, Locale locale) {
    return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern, locale));
  }
}
