package com.ang.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class ErrorDetail {

    private final String location;
    private final String param;

    @JsonProperty("msg")
    private final String message;

    private final String value;

}
