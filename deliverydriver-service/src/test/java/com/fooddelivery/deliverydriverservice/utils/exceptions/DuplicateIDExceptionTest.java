package com.fooddelivery.deliverydriverservice.utils.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateIDExceptionTest {

    @Test
    public void testDefaultConstructor() {
        DuplicateIDException exception = new DuplicateIDException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testMessageConstructor() {
        String message = "Test message";
        DuplicateIDException exception = new DuplicateIDException(message);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testCauseConstructor() {
        Throwable cause = new Throwable("Test cause");
        DuplicateIDException exception = new DuplicateIDException(cause);
        assertEquals("java.lang.Throwable: Test cause", exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        String message = "Test message";
        Throwable cause = new Throwable("Test cause");
        DuplicateIDException exception = new DuplicateIDException(message, cause);
        assertEquals(message, exception.getMessage());
        assertSame(cause, exception.getCause());
    }

}