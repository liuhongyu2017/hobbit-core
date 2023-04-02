package org.hobbit.core.launch.server;

import org.hobbit.core.launch.utils.INetUtil;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;

/**
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@AutoConfiguration
public class ServerInfo implements SmartInitializingSingleton {

  private final ServerProperties serverProperties;

  private String hostName;

  private String ip;

  private Integer port;

  private String ipWithPort;

  @Autowired(required = false)
  public ServerInfo(ServerProperties serverProperties) {
    this.serverProperties = serverProperties;
  }

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
