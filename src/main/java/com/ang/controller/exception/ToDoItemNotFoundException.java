package com.ang.controller.exception;

import lombok.Data;

@Data
public class ToDoItemNotFoundException extends RuntimeException {
    public final int id;
}
