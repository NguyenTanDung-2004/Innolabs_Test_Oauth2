package com.example.Mini_Project1.validator;

import java.util.Objects;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LinkValidator implements ConstraintValidator<LinkConstraint, String> {

	private static final String URL_PATTERN = "^(http|https)://.*$"; // Basic URL regex
    
	private Pattern pattern;
	
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) { // best practice: 1 validator class is only used to validate 1 logic.
            return true;
        }
        return pattern.matcher(value).matches();
    }
    
    @Override
    public void initialize(LinkConstraint constraintAnnotation) {
        this.pattern = Pattern.compile(URL_PATTERN); // Initialize the pattern here
    }


}