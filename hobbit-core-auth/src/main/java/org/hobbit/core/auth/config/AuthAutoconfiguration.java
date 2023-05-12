package org.hobbit.core.auth.config;

import org.hobbit.core.auth.props.TokenProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author lhy
 * @version 1.0.0 2023/5/10
 */
@EnableConfigurationProperties(TokenProperties.class)
@AutoConfiguration
public class AuthAutoconfiguration {

}
