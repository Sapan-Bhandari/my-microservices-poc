package com.example.order.model;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private Long productId;  // Reference to Product
    @Getter @Setter
    private Integer quantity;
    
    // Default constructor (required by JPA)
    public Order() {
    }

    // Parameterized constructor
    public Order(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    
    // All-arguments constructor
    public Order(Long id, Long productId, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }
}