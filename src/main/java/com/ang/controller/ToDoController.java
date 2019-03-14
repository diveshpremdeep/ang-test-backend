package com.ang.controller;

import com.ang.controller.exception.ToDoItemNotFoundException;
import com.ang.model.ToDoItem;
import com.ang.model.ToDoItemAddRequest;
import com.ang.model.ToDoItemUpdateRequest;
import com.ang.service.ToDoService;
import com.ang.util.exception.InvalidInputException;
import com.google.common.base.Preconditions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<ToDoItem> getToDoItem(@PathVariable("id") String idStr) {
        final int idInt = validateAndNumerifyId(idStr);

        final ToDoItem item = toDoService.getToDoItem(idInt)
            .orElseThrow(() -> new ToDoItemNotFoundException(idInt));

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<ToDoItem> updateToDoItem(@PathVariable("id") String idStr,
                                                   @RequestBody ToDoItemUpdateRequest updateReq) {
        final int idInt = validateAndNumerifyId(idStr);

        final ToDoItem item = toDoService.updateToDoItem(idInt, updateReq)
            .orElseThrow(() -> new ToDoItemNotFoundException(idInt));

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(item);
    }

    private int validateAndNumerifyId(String idStr) {
        return Optional.ofNullable(idStr)
            .map(String::trim)
            .flatMap(this::parseIntOpt)
            .orElseThrow(() -> new InvalidInputException(
                idStr,
                "The ID should be a valid 32-bit number"
            ));
    }

    // Very hacky, but keeping it simple for now.
    private Optional<Integer> parseIntOpt(String str) {
        try {
            return Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }
}
