package org.hobbit.core.boot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.launch.props.HobbitPropertySource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
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
public class HobbitBootAutoConfiguration {

}
