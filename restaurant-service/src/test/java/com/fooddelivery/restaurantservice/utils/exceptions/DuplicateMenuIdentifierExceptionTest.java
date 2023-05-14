package com.fooddelivery.restaurantservice.utils.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateMenuIdentifierExceptionTest {
    @Test
    public void testConstructorWithNoArgs() {
        DuplicateMenuIdentifierException exception = new DuplicateMenuIdentifierException();
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    public void testConstructorWithMessage() {
        String message = "Duplicate detected";
        DuplicateMenuIdentifierException exception = new DuplicateMenuIdentifierException(message);
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    public void testConstructorWithCause() {
        String causeMessage = "root cause";
        Throwable cause = new RuntimeException(causeMessage);
        DuplicateMenuIdentifierException exception = new DuplicateMenuIdentifierException(cause);
        Assertions.assertEquals(cause, exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String message = "Duplicate GameID detected";
        String causeMessage = "root cause";
        Throwable cause = new RuntimeException(causeMessage);
        DuplicateMenuIdentifierException exception = new DuplicateMenuIdentifierException(message, cause);
        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(cause, exception.getCause());
    }
}