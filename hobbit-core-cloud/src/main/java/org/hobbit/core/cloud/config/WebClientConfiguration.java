package org.hobbit.core.cloud.config;

import lombok.RequiredArgsConstructor;
import org.hobbit.core.cloud.props.HobbitFeignHeadersProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(HobbitFeignHeadersProperties.class)
public class WebClientConfiguration {

}
