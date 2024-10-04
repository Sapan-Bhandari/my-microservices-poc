package com.example.product.controller;

import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(100.0);
        
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(200.0);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Act
        List<Product> products = productController.getAllProducts();

        // Assert
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testCreateProduct() {
        // Arrange
        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(100.0);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product createdProduct = productController.createProduct(product);

        // Assert
        assertNotNull(createdProduct);
        assertEquals("Product 1", createdProduct.getName());
        assertEquals(100.0, createdProduct.getPrice());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct_Success() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Product");
        existingProduct.setPrice(100.0);

        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(150.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // Act
        Product result = productController.updateProduct(productId, updatedProduct);

        // Assert
        assertEquals("Updated Product", result.getName());
        assertEquals(150.0, result.getPrice());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testUpdateProduct_ProductNotFound() {
        // Arrange
        Long productId = 1L;
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(150.0);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productController.updateProduct(productId, updatedProduct);
        });
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        // Act
        String response = productController.deleteProduct(productId);

        // Assert
        assertEquals("Product deleted successfully", response);
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProduct_ProductNotFound() {
        // Arrange
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productController.deleteProduct(productId);
        });
        verify(productRepository, times(1)).existsById(productId);
    }
}