package org.hobbit.core.tool.jackson.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hobbit.core.tool.jackson.desensitization.Desensitization;
import org.hobbit.core.tool.jackson.serizlizer.ObjectDesensitizeSerializer;

/**
 * 脱敏注解
 *
 * @author lhy
 * @version 1.0.0 2023/4/25
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = ObjectDesensitizeSerializer.class)
@Documented
public @interface Desensitize {

  /**
   * 脱敏实现
   */
  Class<? extends Desensitization<?>> desensitization();
}
