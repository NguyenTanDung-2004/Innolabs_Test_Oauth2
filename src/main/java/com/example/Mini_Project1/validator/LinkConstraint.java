package com.example.Mini_Project1.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = LinkValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME) // 1 annotation can be validate
                                                     // by many validators.
public @interface LinkConstraint {
    // default
    String message() default "Invalid link format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}