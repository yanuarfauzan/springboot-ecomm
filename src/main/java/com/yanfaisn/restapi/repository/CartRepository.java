package com.yanfaisn.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yanfaisn.restapi.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUserIdAndProductId(String username, String productId);

    List<Cart> findByUserId(String username);
}
