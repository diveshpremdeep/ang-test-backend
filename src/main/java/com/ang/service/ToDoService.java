package com.ang.service;

import com.ang.model.ToDoItem;
import com.ang.model.ToDoItem.ToDoItemBuilder;
import com.ang.util.exception.InvalidInputException;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;

@Component
public final class ToDoService {
    private static final int MIN_STRING_LENGTH = 1;
    private static final int MAX_STRING_LENGTH = 50;

    // We could simply use an incrementing atomic integer for generating sequential IDs, but this prevents us from
    // potentially reusing IDs that belonged to deleted todo items.
    private final Random idGenerator = new Random();

    // In an ideal world, such a service would talk to a persistent store like a SQL/NoSQL database, but
    // we're keeping things simple for this demo and using an in-memory map to store to-do items.
    // As a side effect, this implementation will not survive service restarts.
    public final ConcurrentMap<Integer, ToDoItem> todoItems = Maps.newConcurrentMap();

    public ToDoItem addToDoItem(String text) {
        final String itemText = Optional.ofNullable(text)
            .map(String::trim)
            .filter(x -> x.length() >= MIN_STRING_LENGTH && x.length() <= MAX_STRING_LENGTH)
            .orElseThrow(() -> new InvalidInputException(
                text,
                String.format("Must be between %d and %d chars long", MIN_STRING_LENGTH, MAX_STRING_LENGTH)
            ));

        final ToDoItemBuilder builder = ToDoItem.builder()
            .text(itemText)
            .createdAt(new Date());

        return addToDoItem(builder);
    }

    private ToDoItem addToDoItem(ToDoItemBuilder itemBuilder) {
        final int id = generateId();
        final ToDoItem item = itemBuilder.id(id).build();

        final Optional<ToDoItem> previousItemOpt = Optional.ofNullable(todoItems.putIfAbsent(id, item));

        if (previousItemOpt.isPresent()) {
            // A todo item with the given ID already exists, so try creating a todo item with a new ID.
            return addToDoItem(itemBuilder);
        }

        // Fetch the todo item again to get any recent updates before returning it.
        return todoItems.get(id);

    }

    private int generateId() {
        return idGenerator.nextInt();
    }
}
