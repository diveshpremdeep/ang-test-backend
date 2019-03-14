package com.ang.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@Builder
public class ToDoItem {

    private final int id;
    private final String text;

    @JsonProperty("isCompleted")
    private final boolean completed;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private final Date createdAt;

}
