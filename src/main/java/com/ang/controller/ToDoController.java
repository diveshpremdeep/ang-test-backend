package com.ang.controller;

import com.ang.controller.exception.ToDoItemNotFoundException;
import com.ang.model.ToDoItem;
import com.ang.model.ToDoItemAddRequest;
import com.ang.service.ToDoService;
import com.google.common.base.Preconditions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ToDoItem> addToDoItem(@PathVariable("id") Integer id) {
        final ToDoItem item = toDoService.getToDoItem(id).orElseThrow(() -> new ToDoItemNotFoundException(id));

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(item);
    }
}
