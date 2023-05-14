package com.fooddelivery.deliverydriverservice.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpErrorInfoTest {
    @Test
    void testHttpErrorInfo() {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        String path = "/games/1";
        String message = "Game not found";
        ZonedDateTime timestamp = ZonedDateTime.now();

        HttpErrorInfo errorInfo = new HttpErrorInfo(httpStatus, path, message);

        assertEquals(httpStatus, errorInfo.getHttpStatus());
        assertEquals(path, errorInfo.getPath());
        assertEquals(message, errorInfo.getMessage());
        assertTrue(errorInfo.getTimestamp().isAfter(timestamp.minusSeconds(1)));
        assertTrue(errorInfo.getTimestamp().isBefore(timestamp.plusSeconds(1)));
    }
}