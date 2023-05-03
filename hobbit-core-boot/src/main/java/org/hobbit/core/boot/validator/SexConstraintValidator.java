package org.hobbit.core.boot.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author lhy
 * @version 1.0.0 2023/5/3
 */
public class SexConstraintValidator implements ConstraintValidator<Sex, String> {

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return s != null && (s.equals("男") || s.equals("女"));
  }
}
