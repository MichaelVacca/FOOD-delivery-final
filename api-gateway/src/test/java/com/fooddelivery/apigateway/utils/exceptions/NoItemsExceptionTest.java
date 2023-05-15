package com.fooddelivery.apigateway.utils.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoItemsExceptionTest {

    @Test
    public void testDefaultConstructor() {
        NoItemsException exception = new NoItemsException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
    @Test
    public void testMessageConstructor() {
        String errorMessage = "No items!";
        NoItemsException exception = new NoItemsException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }
    @Test
    public void constructor_setsCause() {
        Throwable cause = new RuntimeException("Something went wrong");
        NoItemsException exception = new NoItemsException(cause);
        assertEquals(cause, exception.getCause());
    }
    @Test
    public void testMessageAndCauseConstructor() {
        String errorMessage = "No items!";
        Throwable cause = new IllegalArgumentException("Invalid input");
        NoItemsException exception = new NoItemsException(errorMessage, cause);
        assertEquals(errorMessage, exception.getMessage());
        assertSame(cause, exception.getCause());
    }

}