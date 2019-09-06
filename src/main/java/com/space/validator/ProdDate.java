package com.space.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ProdDateValidator.class)
@Documented

public @interface ProdDate {
    String message() default "Date/year is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
