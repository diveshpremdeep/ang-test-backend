package com.ang.controller;

import com.ang.model.ToDoItem;
import com.ang.model.ToDoItemAddRequest;
import com.ang.service.ToDoService;
import com.google.common.base.Preconditions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class ToDoController {
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = Preconditions.checkNotNull(toDoService);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ToDoItem> addToDoItem(@RequestBody ToDoItemAddRequest request) {
        final ToDoItem item = toDoService.addToDoItem(request.getText());

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(item);
    }
}
