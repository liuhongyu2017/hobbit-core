package org.hobbit.core.cloud.config;

import org.hobbit.core.launch.props.HobbitPropertySource;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * hobbit cloud 增强配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/20
 */
@HobbitPropertySource(value = "classpath:/hobbit-cloud.yml")
@AutoConfiguration
public class HobbitCloudAutoConfiguration {

}
