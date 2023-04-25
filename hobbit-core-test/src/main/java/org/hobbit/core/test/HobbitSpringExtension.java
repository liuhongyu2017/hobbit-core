package org.hobbit.core.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;
import org.hobbit.core.launch.HobbitApplication;
import org.hobbit.core.launch.constant.AppConstant;
import org.hobbit.core.launch.service.LauncherService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author lhy
 * @version 1.0.0 2023/4/25
 */
public class HobbitSpringExtension extends SpringExtension {

  @Override
  public void beforeAll(@NonNull ExtensionContext context) throws Exception {
    super.beforeAll(context);
    setUpTestClass(context);
  }

  private void setUpTestClass(ExtensionContext context) {
    Class<?> clazz = context.getRequiredTestClass();
    HobbitBootTest hobbitBootTest = AnnotationUtils.getAnnotation(clazz, HobbitBootTest.class);
    if (hobbitBootTest == null) {
      throw new HobbitBootTestException(String.format("%s must be @HobbitBootTest .", clazz));
    }
    String appName = hobbitBootTest.appName();
    String profile = hobbitBootTest.profile();
    boolean isLocalDev = HobbitApplication.isLocalDev();
    Properties props = System.getProperties();
    props.setProperty("hobbit.env", profile);
    props.setProperty("hobbit.name", appName);
    props.setProperty("hobbit.is-local", String.valueOf(isLocalDev));
    props.setProperty("hobbit.dev-mode", profile.equals(AppConstant.PROD_CODE) ? "false" : "true");
    props.setProperty("hobbit.service.version", AppConstant.APPLICATION_VERSION);
    props.setProperty("spring.application.name", appName);
    props.setProperty("spring.profiles.active", profile);
    props.setProperty("info.version", AppConstant.APPLICATION_VERSION);
    props.setProperty("info.desc", appName);
    props.setProperty("loadbalancer.client.name", appName);
    // 加载自定义组件
    if (hobbitBootTest.enableLoader()) {
      SpringApplicationBuilder builder = new SpringApplicationBuilder(clazz);
      List<LauncherService> launcherList = new ArrayList<>();
      ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
      launcherList.stream().sorted(Comparator.comparing(LauncherService::getOrder)).toList()
          .forEach(
              launcherService -> launcherService.launcher(builder, appName, profile, isLocalDev));
    }
    System.err.printf("---[junit.test]:[%s]---启动中，读取到的环境变量:[%s]%n", appName, profile);
  }
}
