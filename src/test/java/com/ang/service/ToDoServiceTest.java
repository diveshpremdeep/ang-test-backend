package com.ang.service;

import com.ang.model.ToDoItem;
import com.ang.model.ToDoItemUpdateRequest;
import com.ang.util.exception.InvalidInputException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDoServiceTest {

    @Autowired
    private ToDoService toDoService;


    @Test
    public void testAddToDoItem() {
        final String text = "Call Mom!";

        final ToDoItem item = toDoService.addToDoItem(text);
        assertEquals("Created todo item has incorrect text!", text, item.getText());
        assertFalse("A new todo item must not be in a completed state!", item.isCompleted());
        assertNotNull("A new todo item must have a createdAt date!", item.getCreatedAt());
        assertTrue("A new todo item must have a positive ID!", item.getId() >= 0);
    }

    @Test(expected = InvalidInputException.class)
    public void testAddToDoItemWithNullInput() {
        toDoService.addToDoItem(null);
        fail("Expected null input to fail validation!");
    }

    @Test(expected = InvalidInputException.class)
    public void testAddToDoItemWithEmptyInput() {
        toDoService.addToDoItem("");
        fail("Expected empty input to fail validation!");
    }

    @Test(expected = InvalidInputException.class)
    public void testAddToDoItemWithGiganticInput() {
        // Use a string having length > 50 chars.
        // Could probably use a string generator here; keeping things simple for now.
        final String input = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
        toDoService.addToDoItem(input);

        fail("Expected gigantic input to fail validation!");
    }

    @Test
    public void testGetToDoItem() {
        final String text = "Call Mom!";

        final int id = toDoService.addToDoItem(text).getId();

        final Optional<ToDoItem> item = toDoService.getToDoItem(id);
        assertTrue("Expected to retrieve a todo item!", item.isPresent());
    }

    @Test
    public void testGetToDoItemWithNonExistentId() {
        final Optional<ToDoItem> item = toDoService.getToDoItem(-1);
        assertFalse("Did not expect a todo item!", item.isPresent());
    }

    @Test
    public void testUpdateToDoItem() {
        final String text = "Call Mom!";
        final ToDoItem item = toDoService.addToDoItem(text);
        final String updatedText = "Called Mom!";

        final ToDoItemUpdateRequest updateReq = ToDoItemUpdateRequest.builder()
            .text(updatedText)
            .completed(true)
            .build();
        final Optional<ToDoItem> updatedItem = toDoService.updateToDoItem(item.getId(), updateReq);

        assertTrue("Expected an updated todo item!", updatedItem.isPresent());
        assertEquals("Expected the text to be updated!", updatedText, updatedItem.get().getText());
        assertEquals(
            "Expected the creation date to remain unchanged!",
            item.getCreatedAt(),
            updatedItem.get().getCreatedAt());
    }

    @Test(expected = InvalidInputException.class)
    public void testUpdateToDoItemWithNullInput() {
        final String text = "Call Mom!";
        final ToDoItem item = toDoService.addToDoItem(text);

        final ToDoItemUpdateRequest updateReq = ToDoItemUpdateRequest.builder()
            .text(null)
            .completed(true)
            .build();
        toDoService.updateToDoItem(item.getId(), updateReq);
        fail("Expected null input to fail validation!");
    }

    @Test(expected = InvalidInputException.class)
    public void testUpdateToDoItemWithEmptyInput() {
        final String text = "Call Mom!";
        final ToDoItem item = toDoService.addToDoItem(text);

        final ToDoItemUpdateRequest updateReq = ToDoItemUpdateRequest.builder()
            .text("")
            .completed(true)
            .build();
        toDoService.updateToDoItem(item.getId(), updateReq);
        fail("Expected empty input to fail validation!");
    }

    @Test(expected = InvalidInputException.class)
    public void testUpdateToDoItemWithGiganticInput() {
        final String text = "Call Mom!";
        final ToDoItem item = toDoService.addToDoItem(text);

        final String input = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";

        final ToDoItemUpdateRequest updateReq = ToDoItemUpdateRequest.builder()
            .text(input)
            .completed(true)
            .build();
        toDoService.updateToDoItem(item.getId(), updateReq);
        fail("Expected gigantic input to fail validation!");
    }

}
