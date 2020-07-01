package com.example.demo.infrastructure.errors;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FieldErrorResponse {
    private String fieldName;
    private String errorMessage;
}
