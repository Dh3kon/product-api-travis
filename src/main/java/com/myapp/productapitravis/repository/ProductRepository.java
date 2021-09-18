package com.myapp.productapitravis.repository;

import com.myapp.productapitravis.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
