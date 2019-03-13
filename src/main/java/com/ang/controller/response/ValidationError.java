package com.ang.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ValidationError {

    private final ErrorDetail errorDetail;
    private final String name;

}
