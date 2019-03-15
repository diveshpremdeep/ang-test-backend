package com.ang.util;

import com.ang.util.exception.InvalidInputException;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Component
public final class StringValidator {
    // These properties could be configured as application properties.
    private static final int MIN_STRING_LENGTH = 1;
    private static final int MAX_STRING_LENGTH = 100;

    private static final List<Pair<Character, Character>> brackets = Lists.newArrayList(
            new Pair<>('{', '}'),
            new Pair<>('[', ']'),
            new Pair<>('(', ')')
    );

    public StringValidator() {
    }

    public final boolean validateBrackets(String str) {
        final String input = Optional.ofNullable(str)
                .map(String::trim)
                .filter(x -> x.length() >= MIN_STRING_LENGTH && x.length() <= MAX_STRING_LENGTH)
                .orElseThrow(() -> new InvalidInputException(
                        str,
                        String.format("Must be between %d and %d chars long", MIN_STRING_LENGTH, MAX_STRING_LENGTH)
                ));

        final Stack<Character> stack = new Stack<>();

        for (char nextChar : input.toCharArray()) {
            if (isOpeningBracket(nextChar)) {
                stack.push(nextChar);
            } else if (isClosingBracket(nextChar)) {
                if (!stack.empty() && (stack.peek() == openingBracket(nextChar))) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }

        return stack.empty();
    }

    // Checks if a character represents an opening bracket.
    private boolean isOpeningBracket(char c) {
        return brackets.stream()
                .map(Pair::getLeft)
                .anyMatch(x -> x == c);
    }

    // Checks if a character represents a closing bracket.
    private boolean isClosingBracket(char c) {
        return brackets.stream()
                .map(Pair::getRight)
                .anyMatch(x -> x == c);
    }

    // Returns the opening bracket for a given closing bracket.
    private char openingBracket(char c) {
        return brackets.stream()
                .filter(x -> x.getRight() == c)
                .findFirst()
                .get()  // Normally unsafe, but because this method is private, we assume that
                        // 'c' is a valid closing bracket.
                .getLeft();
    }

}
