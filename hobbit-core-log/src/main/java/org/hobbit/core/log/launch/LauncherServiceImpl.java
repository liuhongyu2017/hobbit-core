package org.hobbit.core.log.launch;

import java.util.Properties;
import net.dreamlu.mica.auto.annotation.AutoService;
import org.hobbit.core.launch.service.LauncherService;
import org.hobbit.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author lhy
 * @version 1.0.0 2023/4/19
 */
@AutoService(LauncherService.class)
public class LauncherServiceImpl implements LauncherService {

  @Override
  public void launcher(SpringApplicationBuilder builder, String appName, String profile,
      boolean isLocalDev) {
    Properties props = System.getProperties();
    PropsUtil.setProperty(props, "logging.config", "classpath:log/logback-" + profile + ".xml");
  }
}
