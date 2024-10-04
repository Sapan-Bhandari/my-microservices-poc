package com.example.product.exception;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleProductNotFoundException_ShouldReturnNotFoundStatus() {
        // Arrange
        Long productId = 1L; // Sample product ID
        ProductNotFoundException exception = new ProductNotFoundException(productId);

        // Act
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleProductNotFoundException(exception);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo("Product not found with id: " + productId);
    }
}