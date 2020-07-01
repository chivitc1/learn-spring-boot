package com.example.demo.report.web.validation;

import com.example.demo.report.web.dto.CreateReportParams;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CreateReportParamsValidatorTest {

    @Mock
    ConstraintValidatorContext context;

    @Test
    void nonTrafficIncident_valid() {
        CreateReportParamsValidator validator = new CreateReportParamsValidator();
        CreateReportParams params = new CreateReportParams();
        params.setDateTime(ZonedDateTime.now());
        params.setDescription("A suspect description");
        params.setNumberOfInvoledCars(0);
        params.setTrafficIncident(false);

        boolean result = validator.isValid(params, context);

        assertThat(result).isTrue();
    }

    @Test
    void trafficIncident_valid() {
        CreateReportParamsValidator validator = new CreateReportParamsValidator();
        CreateReportParams params = new CreateReportParams();
        params.setDateTime(ZonedDateTime.now());
        params.setDescription("A suspect description");
        params.setNumberOfInvoledCars(2);
        params.setTrafficIncident(true);

        boolean result = validator.isValid(params, context);

        assertThat(result).isTrue();
    }

    @Test
    void trafficIncidentWithoutNumberOfCarsInvolved_invalid() {
        CreateReportParamsValidator validator = new CreateReportParamsValidator();
        CreateReportParams params = new CreateReportParams();
        params.setDateTime(ZonedDateTime.now());
        params.setDescription("A suspect description");
        params.setNumberOfInvoledCars(0);
        params.setTrafficIncident(true);

        boolean result = validator.isValid(params, context);

        assertThat(result).isFalse();
    }
}