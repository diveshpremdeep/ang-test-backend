package com.ang.util;

import com.ang.util.exception.InvalidInputException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringValidatorTest {

    private StringValidator validator;

    @Before
    public void setUp() {
        this.validator = new StringValidator();
    }

    @Test
    public void testValidateBracketsWithValidinput() {
        assertTrue(validator.validateBrackets("a"));
        assertTrue(validator.validateBrackets("abcd efgh"));
        assertTrue(validator.validateBrackets("[]"));
        assertTrue(validator.validateBrackets("{}"));
        assertTrue(validator.validateBrackets("()"));
        assertTrue(validator.validateBrackets("[]{}"));
        assertTrue(validator.validateBrackets("{}()"));
        assertTrue(validator.validateBrackets("[()]"));
        assertTrue(validator.validateBrackets("({[]}) "));
        assertTrue(validator.validateBrackets("[](){}"));
        assertTrue(validator.validateBrackets("[](){}"));
        assertTrue(validator.validateBrackets("if (a > b) {yeah} else {oh no!} "));

        assertFalse(validator.validateBrackets("["));
        assertFalse(validator.validateBrackets("{"));
        assertFalse(validator.validateBrackets("("));
        assertFalse(validator.validateBrackets("]"));
        assertFalse(validator.validateBrackets("}"));
        assertFalse(validator.validateBrackets(")"));
        assertFalse(validator.validateBrackets("][}{"));
        assertFalse(validator.validateBrackets("[(])"));
    }

    @Test(expected = InvalidInputException.class)
    public void testValidateBracketsWithNullInput() {
        validator.validateBrackets(null);
        fail("Expected null input to fail validation!");
    }

    @Test(expected = InvalidInputException.class)
    public void testValidateBracketsWithEmptyInput() {
        validator.validateBrackets("");
        fail("Expected empty input to fail validation!");
    }

    @Test(expected = InvalidInputException.class)
    public void testValidateBracketsWithGiganticInput() {
        // Use a string having length > 100 chars.
        // Could probably use a string generator here; keeping things simple for now.
        final String input = "abcdefghijklmnopqrstuvwxyz";
        validator.validateBrackets(input + input + input + input);

        fail("Expected gigantic input to fail validation!");
    }
}
