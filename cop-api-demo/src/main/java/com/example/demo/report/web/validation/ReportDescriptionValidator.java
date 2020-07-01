package com.example.demo.report.web.validation;

import lombok.NonNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReportDescriptionValidator implements
        ConstraintValidator<ValidReportDescription, String> {
    @Override
    public void initialize(ValidReportDescription constraintAnnotation) {

    }

    @Override
    public boolean isValid(@NonNull String value, ConstraintValidatorContext context) {
        if (!value.toLowerCase().contains("suspect")) {
            return false;
        }
        return true;
    }
}
