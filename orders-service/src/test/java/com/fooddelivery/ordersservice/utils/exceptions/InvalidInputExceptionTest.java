package com.fooddelivery.ordersservice.utils.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;


class InvalidInputExceptionTest {
    @Test
    public void testConstructorWithNoArguments() {
        InvalidInputException exception = new InvalidInputException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Invalid input";
        InvalidInputException exception = new InvalidInputException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Something went wrong");
        InvalidInputException exception = new InvalidInputException(cause);
        assertEquals("java.lang.RuntimeException: Something went wrong", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String errorMessage = "Invalid input";
        Throwable cause = new RuntimeException("Something went wrong");
        InvalidInputException exception = new InvalidInputException(errorMessage, cause);
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

}