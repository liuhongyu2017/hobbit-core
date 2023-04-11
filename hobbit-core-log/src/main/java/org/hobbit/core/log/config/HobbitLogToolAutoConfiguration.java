package org.hobbit.core.log.config;

import org.hobbit.core.log.props.HobbitRequestLogProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 日志工具自动配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/10
 */
@AutoConfiguration
@ConditionalOnWebApplication
@EnableConfigurationProperties(HobbitRequestLogProperties.class)
public class HobbitLogToolAutoConfiguration {

}
