package com.example.courseservice.exception.advice;

import com.example.courseservice.exception.custom.InvalidCourseException;
import com.example.courseservice.exception.custom.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ExceptionResponse handleResourceNotFound(ResourceNotFoundException ex) {
        return ExceptionResponse.builder()
                .internalCode("RNF01")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidCourseException.class)
    @ResponseStatus(BAD_REQUEST)
    ExceptionResponse handleResourceNotFound(InvalidCourseException ex) {
        return ExceptionResponse.builder()
                .internalCode("IRE01")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    ExceptionResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        return ExceptionResponse.builder()
                .internalCode("IAE01")
                .message(ex.getMessage())
                .build();
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    ExceptionResponse handleConstraintViolationException(ConstraintViolationException ex) {
        return ExceptionResponse.builder()
                .internalCode("CVE01")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    ExceptionResponse handleAllExceptions(Exception ex) {
        log.error("Generic error", ex);
        return ExceptionResponse.builder()
                .internalCode("GEN01")
                .message("Internal server error")
                .build();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(BAD_REQUEST)
    ExceptionResponse handleBindException(BindException ex) {
        List<FieldError> fieldErrors = new ArrayList<>();
        ex.getFieldErrors().forEach(fieldError ->
                fieldErrors.add(FieldError
                        .builder()
                        .field(fieldError.getField())
                        .errorMessage(fieldError.getDefaultMessage())
                        .build())
        );
        return ExceptionResponse.builder()
                .internalCode("BDE01")
                .message("Bad request for the following reasons:")
                .fieldErrors(fieldErrors)
                .build();
    }
}