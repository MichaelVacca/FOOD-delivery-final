package com.fooddelivery.clientservice.utils.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class InvalidInputExceptionTest {
    @Test
    public void testInvalidInputExceptionDefaultConstructor() {
        InvalidInputException exception = new InvalidInputException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testInvalidInputExceptionConstructorWithMessage() {
        String message = "Invalid input detected";
        InvalidInputException exception = new InvalidInputException(message);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void constructor_setsCause() {
        Throwable cause = new RuntimeException("Something went wrong");
        InvalidInputException exception = new InvalidInputException(cause);
        assertEquals(cause, exception.getCause());
    }


    @Test
    public void testInvalidInputExceptionConstructorWithMessageAndCause() {
        String message = "Invalid input detected";
        Throwable cause = new Throwable("Root cause");
        InvalidInputException exception = new InvalidInputException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}