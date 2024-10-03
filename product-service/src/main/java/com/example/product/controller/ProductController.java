package com.example.product.controller;

import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
    	log.info("Fetching all products");
        return productRepository.findAll();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
    	log.info("Creating a new product");
        return productRepository.save(product);
    }
    
    // Update product (name and/or price)
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
		log.info("Updating product with id: {}", id);
		Optional<Product> existingProductOpt = productRepository.findById(id);

		if (existingProductOpt.isPresent()) {
			Product existingProduct = existingProductOpt.get();

			// Update only if the new value is provided, otherwise retain the existing value
			if (updatedProduct.getName() != null) {
				existingProduct.setName(updatedProduct.getName());
			}

			if (updatedProduct.getPrice() != null) {
				existingProduct.setPrice(updatedProduct.getPrice());
			}

			return productRepository.save(existingProduct);
		} else {
			throw new RuntimeException("Product not found with id: " + id);
		}
	}

	// Delete a product
	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable Long id) {
		log.info("Deleting product with id: {}", id);
		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);
			return "Product deleted successfully";
		} else {
			throw new RuntimeException("Product not found with id: " + id);
		}
	}
}