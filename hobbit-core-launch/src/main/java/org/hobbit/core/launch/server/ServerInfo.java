package org.hobbit.core.launch.server;

import lombok.RequiredArgsConstructor;
import org.hobbit.core.launch.utils.INetUtil;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;

/**
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@RequiredArgsConstructor
@AutoConfiguration
public class ServerInfo implements SmartInitializingSingleton {

  private final ServerProperties serverProperties;

  private String hostName;

  private String ip;

  private Integer port;

  private String ipWithPort;

  @Override
  public void afterSingletonsInstantiated() {
    this.hostName = INetUtil.getHostName();
    this.ip = INetUtil.getHostIp();
    this.port = serverProperties.getPort();
    this.ipWithPort = String.format("%s:%d", ip, port);
  }

  public String getHostName() {
    return hostName;
  }

  public String getIp() {
    return ip;
  }

  public Integer getPort() {
    return port;
  }

  public String getIpWithPort() {
    return ipWithPort;
  }
}
