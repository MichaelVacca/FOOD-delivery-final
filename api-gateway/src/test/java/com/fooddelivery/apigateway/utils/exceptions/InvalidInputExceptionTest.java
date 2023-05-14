package com.fooddelivery.apigateway.utils.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidInputExceptionTest {
    @Test
    public void testDefaultConstructor() {
        InvalidInputException exception = new InvalidInputException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testMessageConstructor() {
        String errorMessage = "Invalid input!";
        InvalidInputException exception = new InvalidInputException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void constructor_setsCause() {
        Throwable cause = new RuntimeException("Something went wrong");
        InvalidInputException exception = new InvalidInputException(cause);
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        String errorMessage = "Invalid input!";
        Throwable cause = new IllegalArgumentException("Invalid input");
        InvalidInputException exception = new InvalidInputException(errorMessage, cause);
        assertEquals(errorMessage, exception.getMessage());
        assertSame(cause, exception.getCause());
    }

}