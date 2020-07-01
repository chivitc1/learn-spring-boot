package com.example.demo.infrastructure.errors;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ErrorResponse {
    private String errorCode;
    private String errorDescription;

    private List<FieldErrorResponse> errors;

}
