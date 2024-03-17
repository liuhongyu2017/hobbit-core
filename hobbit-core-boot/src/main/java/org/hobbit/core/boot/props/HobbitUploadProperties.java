package org.hobbit.core.boot.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
@RefreshScope
@ConfigurationProperties("rapid.upload")
public class HobbitUploadProperties {

}
