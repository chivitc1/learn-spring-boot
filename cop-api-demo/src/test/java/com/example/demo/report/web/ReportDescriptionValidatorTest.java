package com.example.demo.report.web;

import com.example.demo.report.web.validation.ReportDescriptionValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;


class ReportDescriptionValidatorTest {

    @Mock
    ConstraintValidatorContext context;

    @Test
    void givenEmptyString_notValid() {
        ReportDescriptionValidator validator = new ReportDescriptionValidator();

        boolean result = validator.isValid("", context);

        assertThat(result).isFalse();
    }

    @Test
    void givenHasSuspectString_isValid() {
        ReportDescriptionValidator validator = new ReportDescriptionValidator();

        boolean result = validator.isValid("report contains suspect string", context);

        assertThat(result).isTrue();
    }
}