package com.fooddelivery.apigateway.utils.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {

    @Test
    public void testDefaultConstructor() {
        NotFoundException exception = new NotFoundException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testMessageConstructor() {
        String errorMessage = "Not found!";
        NotFoundException exception = new NotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testCauseConstructor() {
        String message = "Entity not found";
        Exception cause = new Exception("Some cause");
        NotFoundException ex = new NotFoundException(message, cause);
        assertEquals(message, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testCauseConstructorWithNullCause() {
        NotFoundException exception = new NotFoundException((Throwable) null);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        String errorMessage = "Not found!";
        Throwable cause = new IllegalArgumentException("Not found");
        NotFoundException exception = new NotFoundException(errorMessage, cause);
        assertEquals(errorMessage, exception.getMessage());
        assertSame(cause, exception.getCause());
    }

}