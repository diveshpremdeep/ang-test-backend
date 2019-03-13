package com.ang.controller;

import com.ang.util.StringValidator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private StringValidator validator;

    public TasksController(StringValidator validator) {
        this.validator = validator;
    }

    @RequestMapping(value = "validateBrackets", method = RequestMethod.GET)
    public ResponseEntity<String> validateBrackets(@RequestParam("input") String input) {
        final boolean valid = validator.validateBrackets(input);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(valid ? "All good!" : "Sorry :-(");
    }
}
