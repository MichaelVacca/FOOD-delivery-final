package com.fooddelivery.apigateway.utils.exceptions;

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
        String errorMessage = "Duplicate username found!";
        DuplicateUserNameException exception = new DuplicateUserNameException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
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
        String errorMessage = "Duplicate username found!";
        Throwable cause = new IllegalArgumentException("Invalid username");
        DuplicateUserNameException exception = new DuplicateUserNameException(errorMessage, cause);
        assertEquals(errorMessage, exception.getMessage());
        assertSame(cause, exception.getCause());
    }

}