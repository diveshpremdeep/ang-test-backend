package com.ang.controller.exception.advice;

import com.ang.controller.exception.ToDoItemNotFoundException;
import com.ang.model.ErrorDetail;
import com.ang.model.ToDoItemErrorDetail;
import com.ang.model.ToDoItemNotFoundError;
import com.ang.model.ValidationError;
import com.ang.util.exception.InvalidInputException;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(ToDoItemNotFoundException.class)
    public final ResponseEntity<ToDoItemNotFoundError> handleNonExistentToDoItem(ToDoItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ToDoItemNotFoundError.builder()
                .name("NotFoundError")
                .errorDetail(ToDoItemErrorDetail.builder()
                    .message(String.format("Item with ID %d not found", ex.getId()))
                    .build())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.TEXT_PLAIN)
            .body("An unexpected error occurred. Please contact the side administrator for more details.");
    }

}
