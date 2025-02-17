package com.example.samplecode.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext cvc) {
        if (phoneNo == null) {
            return false;
        }
//        validate phone numbers of format "1234567890"
        if (phoneNo.matches("^\\d{10}$")) {
            return true;
        }
//        validate phone numbers of format "123-456-7890"
        else if (phoneNo.matches("^\\d{3}-\\d{3}-\\d{4}$")) {
            return true;
        }
        else if (phoneNo.matches("^\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}$")) {
            return true;
        } else if (phoneNo.matches("^\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}$")) {
            return true;
        } else if (phoneNo.matches("^\\(\\d{3}\\)-\\d{3}-\\d{4}$")) {
            return true;
        } else {
            return false;
        }
    }
}
