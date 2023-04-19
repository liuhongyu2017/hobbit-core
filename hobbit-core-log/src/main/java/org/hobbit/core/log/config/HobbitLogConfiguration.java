package org.hobbit.core.log.config;

import org.hobbit.core.launch.props.HobbitPropertySource;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * @author lhy
 * @version 1.0.0 2023/4/19
 */
@AutoConfiguration
@HobbitPropertySource("classpath:/hobbit-log.yml")
public class HobbitLogConfiguration {

}
