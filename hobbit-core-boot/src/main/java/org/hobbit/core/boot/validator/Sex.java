package org.hobbit.core.boot.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 性别约束
 *
 * @author lhy
 * @version 1.0.0 2023/5/3
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SexConstraintValidator.class)
public @interface Sex {

  String message() default "性别有误";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
