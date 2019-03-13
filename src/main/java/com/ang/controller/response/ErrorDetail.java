package com.ang.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorDetail {

    private final String location;
    private final String param;
    private final String message;
    private final String value;

}
