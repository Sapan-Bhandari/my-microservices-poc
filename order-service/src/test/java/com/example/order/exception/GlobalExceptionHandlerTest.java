package com.example.order.exception;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleOrderNotFoundException_ShouldReturnNotFoundStatus() {
        // Arrange
        Long orderId = 1L; // Sample order ID
        OrderNotFoundException exception = new OrderNotFoundException(orderId);

        // Act
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleOrderNotFoundException(exception);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo("Order not found with id: " + orderId);
    }
}