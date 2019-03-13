package com.ang.util.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class InvalidInputException extends RuntimeException {

    private final String input;
    private final String errorMessage;

    public InvalidInputException(String input) {
        this(input, null);
    }

}
