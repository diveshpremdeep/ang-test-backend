package com.ang.service;

import com.ang.model.ToDoItem;
import com.ang.util.exception.InvalidInputException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        assertNotNull("A new todo item muts have a createdAt date!", item.getCreatedAt());
    }

    @Test(expected = InvalidInputException.class)
    public void testAddToDoItemWithNullInput() {
        final ToDoItem item = toDoService.addToDoItem(null);
        fail("Expected null input to fail validation!");
    }

    @Test(expected = InvalidInputException.class)
    public void testAddToDoItemWithEmptyInput() {
        final ToDoItem item = toDoService.addToDoItem("");
        fail("Expected empty input to fail validation!");
    }

    @Test(expected = InvalidInputException.class)
    public void testAddToDoItemWithGiganticInput() {
        // Use a string having length > 50 chars.
        // Could probably use a string generator here; keeping things simple for now.
        final String input = "abcdefghijklmnopqrstuvwxyz";
        toDoService.addToDoItem(input + input);

        fail("Expected gigantic input to fail validation!");
    }
}
