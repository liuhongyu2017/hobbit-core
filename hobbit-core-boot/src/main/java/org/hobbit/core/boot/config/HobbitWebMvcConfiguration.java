package org.hobbit.core.boot.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lhy
 * @version 1.0.0 2023/1/13
 */
@Slf4j
@AutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
public class HobbitWebMvcConfiguration implements WebMvcConfigurer {

}
