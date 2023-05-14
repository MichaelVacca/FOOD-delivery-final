package com.fooddelivery.restaurantservice.utils.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NotFoundExceptionTest {
    @Test
    public void testNoArgsConstructor() {
        NotFoundException ex = new NotFoundException();
        assertNull(ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    public void testMessageConstructor() {
        String message = "Entity not found";
        NotFoundException ex = new NotFoundException(message);
        assertEquals(message, ex.getMessage());
        assertNull(ex.getCause());
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
    public void testMessageAndCauseConstructor() {
        String message = "Entity not found";
        Exception cause = new Exception("Some cause");
        NotFoundException ex = new NotFoundException(message, cause);
        assertEquals(message, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void constructor_setsCause() {
        Throwable cause = new RuntimeException("Not found");
        NotFoundException exception = new NotFoundException(cause);

        assertEquals(cause, exception.getCause());
    }
}