package com.ang.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ToDoItem {

    private final int id;
    private final String text;

    @JsonProperty("isCompleted")
    private final boolean completed;

    private final Date createdAt;

}
