package com.ang.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringValidatorTest {

    @Test
    public void testValidateBracketsWithValidinput() {
        final StringValidator validator = new StringValidator();
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

    @Test(expected = IllegalArgumentException.class)
    public void testValidateBracketsWithNullInput() {
        final StringValidator validator = new StringValidator();

        validator.validateBrackets(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateBracketsWithEmptyInput() {
        final StringValidator validator = new StringValidator();

        validator.validateBrackets("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateBracketsWithGiganticInput() {
        final StringValidator validator = new StringValidator();

        // Use a string having length > 100 chars.
        // Could probably use a string generator here; keeping things simple for now.
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";
        validator.validateBrackets(alphabet + alphabet + alphabet + alphabet);
    }
}
