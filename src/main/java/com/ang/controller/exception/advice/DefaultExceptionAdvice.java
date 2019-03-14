package com.ang.controller.exception.advice;

import com.ang.model.ErrorDetail;
import com.ang.model.ValidationError;
import com.ang.util.exception.InvalidInputException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionAdvice {

    @ExceptionHandler(InvalidInputException.class)
    public final ResponseEntity<ValidationError> handleInvalidInput(InvalidInputException ex) {
        return ResponseEntity.badRequest()
            .contentType(MediaType.APPLICATION_JSON)
            .body(ValidationError.builder()
                .name("ValidationError")
                .errorDetail(ErrorDetail.builder()
                    .location("params")
                    .param("input")
                    .message(ex.getErrorMessage())
                    .value(ex.getInput())
                    .build())
                .build());
    }
}
