package org.hobbit.core.boot.config;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.launch.props.HobbitPropertySource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@HobbitPropertySource(value = "classpath:/hobbit-boot.yml")
public class HobbitBootAutoConfiguration implements
    WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

  @Override
  public void customize(UndertowServletWebServerFactory factory) {
    factory.addDeploymentInfoCustomizers(deploymentInfo -> {
      WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
      webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
      deploymentInfo.addServletContextAttribute(
          "io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
    });
  }
}
