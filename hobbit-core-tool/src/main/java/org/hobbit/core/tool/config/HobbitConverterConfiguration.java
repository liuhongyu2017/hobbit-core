package org.hobbit.core.tool.config;

import org.hobbit.core.tool.convert.EnumToStringConverter;
import org.hobbit.core.tool.convert.StringToEnumConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * hobbit enum 《-》 String 转换配置
 *
 * @author L.cm
 */
@AutoConfiguration
public class HobbitConverterConfiguration implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new EnumToStringConverter());
    registry.addConverter(new StringToEnumConverter());
  }

}
