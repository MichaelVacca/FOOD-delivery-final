package com.fooddelivery.clientservice.utils.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateUserNameExceptionTest {

    @Test
    public void testDefaultConstructor() {
        DuplicateUserNameException exception = new DuplicateUserNameException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testMessageConstructor() {
        String message = "Test message";
        DuplicateUserNameException exception = new DuplicateUserNameException(message);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testCauseConstructor() {
        Throwable cause = new Throwable("Test cause");
        DuplicateUserNameException exception = new DuplicateUserNameException(cause);
        assertEquals("java.lang.Throwable: Test cause", exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        String message = "Test message";
        Throwable cause = new Throwable("Test cause");
        DuplicateUserNameException exception = new DuplicateUserNameException(message, cause);
        assertEquals(message, exception.getMessage());
        assertSame(cause, exception.getCause());
    }

}