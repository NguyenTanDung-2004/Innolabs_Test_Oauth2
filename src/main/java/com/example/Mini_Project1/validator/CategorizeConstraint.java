package com.example.Mini_Project1.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.Mini_Project1.validator.dob_constraint.MinAgeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CategorizeValidator.class }) // 1 annotation can be validate
                                                     // by many validators.
public @interface CategorizeConstraint {
    // default
    String message() default "Categorize must be in Technology, Health, Business";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}