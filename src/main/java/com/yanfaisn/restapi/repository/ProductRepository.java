package com.yanfaisn.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yanfaisn.restapi.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsById(String id);
}
