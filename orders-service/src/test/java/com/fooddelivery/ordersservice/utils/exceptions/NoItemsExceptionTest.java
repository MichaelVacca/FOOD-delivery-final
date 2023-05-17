package com.fooddelivery.ordersservice.utils.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NoItemsExceptionTest {

    @Test
    public void testConstructorWithNoArguments() {
        NoItemsException exception = new NoItemsException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "No items found";
        NoItemsException exception = new NoItemsException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Something went wrong");
        NoItemsException exception = new NoItemsException(cause);
        assertEquals("java.lang.RuntimeException: Something went wrong", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String errorMessage = "No items found";
        Throwable cause = new RuntimeException("Something went wrong");
        NoItemsException exception = new NoItemsException(errorMessage, cause);
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}