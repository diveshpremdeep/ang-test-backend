package com.ang.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ToDoItemUpdateRequest {

    private String text;

    @JsonProperty("isCompleted")
    private boolean completed;

}
