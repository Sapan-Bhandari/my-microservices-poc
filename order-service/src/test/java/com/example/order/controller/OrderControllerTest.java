package com.example.order.controller;

import com.example.order.exception.OrderNotFoundException;
import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHome() {
        String result = orderController.home();
        assertEquals("Welcome to the Application!", result);
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order(1L, 101L, 10);
        Order order2 = new Order(2L, 102L, 5);
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> result = orderController.getAllOrders();
        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testCreateOrder() {
        Order newOrder = new Order(1L, 101L, 10);
        when(orderRepository.save(any(Order.class))).thenReturn(newOrder);

        Order result = orderController.createOrder(newOrder);
        assertEquals(1L, result.getId());
        assertEquals(101L, result.getProductId());
        assertEquals(10, result.getQuantity());
        verify(orderRepository, times(1)).save(newOrder);
    }

    @Test
    void testUpdateOrder_Success() {
        Long orderId = 1L;
        Order existingOrder = new Order(orderId, 101L, 10);
        Order updatedOrderDetails = new Order(orderId, 101L, 15);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(existingOrder);

        ResponseEntity<Order> response = orderController.updateOrder(orderId, updatedOrderDetails);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(15, response.getBody().getQuantity());

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(existingOrder);
    }

    @Test
    void testUpdateOrder_NotFound() {
        Long orderId = 1L;
        Order updatedOrderDetails = new Order(orderId, 101L, 15);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderController.updateOrder(orderId, updatedOrderDetails);
        });

        assertEquals("Order not found with id: " + orderId, exception.getMessage());

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testDeleteOrder_Success() {
        Long orderId = 1L;
        when(orderRepository.existsById(orderId)).thenReturn(true);

        ResponseEntity<Void> response = orderController.deleteOrder(orderId);
        assertEquals(204, response.getStatusCodeValue());

        verify(orderRepository, times(1)).existsById(orderId);
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    void testDeleteOrder_NotFound() {
        Long orderId = 1L;
        when(orderRepository.existsById(orderId)).thenReturn(false);

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderController.deleteOrder(orderId);
        });

        assertEquals("Order not found with id: " + orderId, exception.getMessage());

        verify(orderRepository, times(1)).existsById(orderId);
        verify(orderRepository, never()).deleteById(orderId);
    }
    
    @Test
    void testDefaultConstructor() {
        // Arrange
        Order order = new Order(); // Using the default constructor

        // Act & Assert
        assertEquals(null, order.getId());
        assertEquals(null, order.getProductId());
        assertEquals(null, order.getQuantity());
    }

    @Test
    void testParameterizedConstructor() {
        // Arrange
        Long productId = 101L;
        Integer quantity = 10;
        Order order = new Order(productId, quantity); // Using the parameterized constructor

        // Act & Assert
        assertEquals(null, order.getId()); // ID is not set by this constructor
        assertEquals(productId, order.getProductId());
        assertEquals(quantity, order.getQuantity());
    }

    @Test
    void testAllArgumentsConstructor() {
        // Arrange
        Long id = 1L;
        Long productId = 101L;
        Integer quantity = 10;
        Order order = new Order(id, productId, quantity); // Using the all-arguments constructor

        // Act & Assert
        assertEquals(id, order.getId());
        assertEquals(productId, order.getProductId());
        assertEquals(quantity, order.getQuantity());
    }
}