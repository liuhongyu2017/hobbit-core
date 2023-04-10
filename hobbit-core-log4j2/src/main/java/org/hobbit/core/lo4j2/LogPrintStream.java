package org.hobbit.core.lo4j2;

import java.io.PrintStream;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;

/**
 * 替换 系统 System.err 和 System.out 为 log
 *
 * @author lhy
 * @version 1.0.0 2023/4/4
 */
@Slf4j
public class LogPrintStream extends PrintStream {

  private final boolean error;

  private LogPrintStream(boolean error) {
    super(error ? System.err : System.out);
    this.error = error;
  }

  public static LogPrintStream out() {
    return new LogPrintStream(false);
  }

  public static LogPrintStream err() {
    return new LogPrintStream(true);
  }

  @Override
  public void print(String s) {
    if (error) {
      log.error(s);
    } else {
      log.info(s);
    }
  }

  /**
   * 重写掉它，因为它会打印很多无用的新行
   */
  @Override
  public void println() {
  }

  @Override
  public void println(String x) {
    if (error) {
      log.error(x);
    } else {
      log.info(x);
    }
  }

  @SuppressWarnings("all")
  @Override
  public PrintStream printf(String format, Object... args) {
    if (error) {
      log.error(String.format(format, args));
    } else {
      log.info(String.format(format, args));
    }
    return this;
  }

  @SuppressWarnings("all")
  @Override
  public PrintStream printf(Locale l, String format, Object... args) {
    if (error) {
      log.error(String.format(l, format, args));
    } else {
      log.info(String.format(l, format, args));
    }
    return this;
  }
}
