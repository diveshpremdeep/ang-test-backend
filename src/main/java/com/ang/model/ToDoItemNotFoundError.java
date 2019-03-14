package com.ang.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class ToDoItemNotFoundError {

    @JsonProperty("details")
    private final List<ToDoItemErrorDetail> errorDetails;

    private final String name;

}
