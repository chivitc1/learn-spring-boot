package com.example.demo.report.web.validation;

import com.example.demo.report.web.dto.CreateReportParams;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreateReportParamsValidator implements ConstraintValidator<ValidCreateReportParams, CreateReportParams> {

    @Override
    public void initialize(ValidCreateReportParams constraintAnnotation) {

    }

    @Override
    public boolean isValid(CreateReportParams value, ConstraintValidatorContext context) {
        if (value.isTrafficIncident() && value.getNumberOfInvoledCars() <= 0) {
            return false;
        }

        return true;
    }
}
