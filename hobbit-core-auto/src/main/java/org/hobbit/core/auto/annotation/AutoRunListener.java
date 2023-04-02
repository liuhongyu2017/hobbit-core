package org.hobbit.core.auto.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * SpringApplicationRunListener 处理
 *
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface AutoRunListener {

}
