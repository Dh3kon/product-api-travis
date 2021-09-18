package com.myapp.productapitravis.controller;

import com.myapp.productapitravis.entity.Product;
import com.myapp.productapitravis.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findById(productId).get());
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product productToUpd) {
        var productResponseEntity = productRepository.findById(id).map(
                p -> {
                    p.setDescription(productToUpd.getDescription());
                    p.setBrand(productToUpd.getBrand());
                    return productRepository.save(p);
                }
        ).get();
        return ResponseEntity.status(HttpStatus.OK).body(productResponseEntity);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully!");
    }
}
