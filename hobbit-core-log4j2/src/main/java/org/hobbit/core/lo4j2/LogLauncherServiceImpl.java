package org.hobbit.core.lo4j2;

import net.dreamlu.mica.auto.annotation.AutoService;
import org.hobbit.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author lhy
 * @version 1.0.0 2023/4/4
 */
@AutoService(LauncherService.class)
public class LogLauncherServiceImpl implements LauncherService {

  @Override
  public void launcher(SpringApplicationBuilder builder, String appName, String profile,
      boolean isLocalDev) {
    System.setProperty("logging.config", String.format("classpath:log/log4j2_%s.xml", profile));
    // 非本地 将 全部的 System.err 和 System.out 替换为log
    if (!isLocalDev) {
      System.setOut(LogPrintStream.out());
      System.setErr(LogPrintStream.err());
    }
  }
}
