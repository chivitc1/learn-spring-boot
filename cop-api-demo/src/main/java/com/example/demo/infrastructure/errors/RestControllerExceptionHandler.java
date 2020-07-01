package com.example.demo.infrastructure.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import java.util.stream.Collectors;

@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(MethodArgumentNotValidException exception) {

        return ErrorResponse.builder()
                .errorCode("A400")
                .errorDescription("Bad request")
                .errors(exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> new FieldErrorResponse(fieldError.getField(),
                                fieldError.getDefaultMessage()))
                        .collect(Collectors.toList()))
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(BindException exception) {

        return ErrorResponse.builder()
                .errorCode("B400")
                .errorDescription("Bad request")
                .errors(exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> new FieldErrorResponse(fieldError.getField(),
                                fieldError.getDefaultMessage()))
                        .collect(Collectors.toList()))
                .build();
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity handleMultipartException(MultipartException e, Model model) {
        model.addAttribute("exception", e);
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

}
