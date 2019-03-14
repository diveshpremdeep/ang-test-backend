package com.ang.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class ValidationError {

    @JsonProperty("details")
    private final ErrorDetail errorDetail;

    private final String name;

}
