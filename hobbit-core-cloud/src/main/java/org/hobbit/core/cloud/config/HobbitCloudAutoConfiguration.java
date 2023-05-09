package org.hobbit.core.cloud.config;

import org.hobbit.core.launch.constant.AppConstant;
import org.hobbit.core.launch.props.HobbitPropertySource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * hobbit cloud 增强配置
 *
 * @author lhy
 * @version 1.0.0 2023/4/20
 */
@EnableFeignClients(AppConstant.BASE_PACKAGES)
@HobbitPropertySource(value = "classpath:/hobbit-cloud.yml")
@AutoConfiguration
public class HobbitCloudAutoConfiguration {

}
