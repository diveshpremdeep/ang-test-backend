package com.ang.service;

import com.ang.model.ToDoItem;
import com.ang.model.ToDoItem.ToDoItemBuilder;
import com.ang.model.ToDoItemUpdateRequest;
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

    /**
     * Creates a new todo item.
     *
     * @param text The text of the todo item.
     * @return The new todo item.
     */
    public ToDoItem addToDoItem(String text) {
        final String itemText = validateAndTrimText(text);

        final ToDoItemBuilder builder = ToDoItem.builder()
            .text(itemText)
            .createdAt(new Date());

        return addToDoItem(builder);
    }

    /**
     * Returns the todo item having a given id.
     *
     * @param id The ID of the todo item.
     *
     * @return A non-empty {@code Optional} containing the todo item corresponding to the provided id; {@code Optional
     * .empty()} if no todo item having the provided ID could be found.
     */
    public Optional<ToDoItem> getToDoItem(int id) {
        return Optional.ofNullable(todoItems.get(id));
    }

    /**
     *
     * @param id The ID of the todo item to update.
     * @param updateReq Encapsulates the details to update.
     *
     * @return A non-empty {@code Optional} containing the updated todo item corresponding to the provided id; {@code
     * Optional.empty()} if no todo item having the provided ID could be found.
     */
    public Optional<ToDoItem> updateToDoItem(int id, ToDoItemUpdateRequest updateReq) {
        // NOTE - I'm still dubious of the semantics of this method in a multi-threaded setting.
        final Optional<ToDoItem> existing = getToDoItem(id);

        if (!existing.isPresent()) {
            return Optional.empty();
        }

        final String text = validateAndTrimText(updateReq.getText());

        todoItems.replace(
            id,
            ToDoItem.builder()
                .id(id)
                .text(text)
                .completed(updateReq.isCompleted())
                .createdAt(existing.get().getCreatedAt())  // Do we need an "updatedAt" field?
                .build()
        );

        // Because a different thread may replace the item before we exit, re-fetch the item from the store before
        // returning it.
        return getToDoItem(id);
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

    private String validateAndTrimText(String text) {
        return Optional.ofNullable(text)
            .map(String::trim)
            .filter(x -> x.length() >= MIN_STRING_LENGTH && x.length() <= MAX_STRING_LENGTH)
            .orElseThrow(() -> new InvalidInputException(
                text,
                String.format("Must be between %d and %d chars long", MIN_STRING_LENGTH, MAX_STRING_LENGTH)
            ));
    }

    private int generateId() {
        return Math.abs(idGenerator.nextInt());
    }
}
