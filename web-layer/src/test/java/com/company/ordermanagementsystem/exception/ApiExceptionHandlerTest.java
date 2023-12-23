package com.company.ordermanagementsystem.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ApiExceptionHandlerTest {

    private ApiExceptionHandler apiExceptionHandler;

    @BeforeEach
    void setUp() {
        apiExceptionHandler = new ApiExceptionHandler();
    }

    @Test
    void testHandleException() {
        UUID id = UUID.randomUUID();
        OrderNotFoundException expectedException = new OrderNotFoundException(id);
        String expectedMessage = "Order with id " + id + " not found";
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;


        ResponseEntity<Object> response = apiExceptionHandler.handleApiRequestException(expectedException);
        Object body = response.getBody();
        ApiException apiException = (ApiException) response.getBody();
        String actualMessage = apiException.message();
        HttpStatus actualStatus = apiException.httpStatus();

        assertTrue(response.getStatusCode().is4xxClientError());
        assertInstanceOf(ApiException.class, body);
        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedStatus, actualStatus);
        assertTrue(apiException.timestamp().isBefore(ZonedDateTime.now()));
        assertTrue(apiException.timestamp().isAfter(ZonedDateTime.now().minusSeconds(1)));
    }
}