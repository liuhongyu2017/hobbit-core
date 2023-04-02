package org.hobbit.core.tool.config;


import java.util.function.Supplier;
import org.hobbit.core.tool.support.BinderSupplier;
import org.hobbit.core.tool.utils.SpringUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 工具配置类
 *
 * @author Chill
 */
@AutoConfiguration
public class ToolConfiguration {

  /**
   * Spring上下文缓存
   */
  @Bean
  public SpringUtil springUtil() {
    return new SpringUtil();
  }

  /**
   * Binder支持类
   */
  @Bean
  @ConditionalOnMissingBean
  public Supplier<Object> binderSupplier() {
    return new BinderSupplier();
  }

}
