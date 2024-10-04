package com.example.order.controller;

import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import com.example.order.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/")
    public String home() {
        return "Welcome to the Application!";
    }
    
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }
    
 // Update Order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        log.info("Updating order with id: {}", id);

        return orderRepository.findById(id).map(order -> {
            order.setQuantity(orderDetails.getQuantity());
            Order updatedOrder = orderRepository.save(order);
            return ResponseEntity.ok(updatedOrder);
        }).orElseThrow(() -> {
            log.warn("Order with id {} not found", id);
            throw new OrderNotFoundException(id); // Throw custom exception if order not found
        });
    }

    // Delete Order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.info("Deleting order with id: {}", id);

        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            log.info("Order with id {} deleted successfully", id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            log.warn("Order with id {} not found for deletion", id);
            throw new OrderNotFoundException(id); // Throw custom exception if order not found
        }
    }
}