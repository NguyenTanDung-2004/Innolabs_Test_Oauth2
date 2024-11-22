package com.example.Mini_Project1.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.example.Mini_Project1.validator.dob_constraint.DobConstraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategorizeValidator implements ConstraintValidator<CategorizeConstraint, String> {

    private String[] listCategorize = {"Technology", "Health", "Business"};

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) { // best practice: 1 validator class is only used to validate 1 logic.
            return true;
        }
        
        // check 
        for (int i = 0; i < listCategorize.length; i++) {
        	if (value.equals(listCategorize[i])) {
        		return true;
        	}
        }
        
        return false;
    }


}