package com.yanfaisn.restapi.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.yanfaisn.restapi.entity.Cart;
import com.yanfaisn.restapi.entity.Product;
import com.yanfaisn.restapi.entity.Users;
import com.yanfaisn.restapi.repository.CartRepository;
import com.yanfaisn.restapi.repository.ProductRepository;
import com.yanfaisn.restapi.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartSevice {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    public Cart create(String username, String productId, Double quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "product dengan ID " + productId + " tidak ditemukan"));
        Optional<Cart> optional = cartRepository.findByUserIdAndProductId(username, productId);
        Cart cart;
        if (optional.isPresent()) {
            cart = optional.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            cart.setAmount(new BigDecimal(cart.getPrice().doubleValue() * cart.getQuantity()));
            cartRepository.save(cart);
        } else {
            cart = new Cart();
            cart.setId(UUID.randomUUID().toString());
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cart.setPrice(cart.getPrice());
            cart.setAmount(new BigDecimal(cart.getPrice().doubleValue() * cart.getQuantity()));
            cart.setUser(new Users(username));
            cartRepository.save(cart);
        }

        return cart;

    }

    @Transactional
    public Cart updateQuantity(String username, String productId, Double quantity) {
        Cart cart = cartRepository.findByUserIdAndProductId(username, productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Produk dengan ID " + productId + " tidak ditemukan di keranjang " + username));
        cart.setQuantity(quantity);
        cart.setAmount(new BigDecimal(cart.getPrice().doubleValue() * cart.getQuantity()));
        cartRepository.save(cart);

        return cart;
    }

    public void delete(String username, String productId) {
        Cart cart = cartRepository.findByUserIdAndProductId(username, productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Produk dengan ID " + productId + " tidak ditemukan di keranjang " + username));
        cartRepository.delete(cart);
    }

    public List<Cart> findByUserId(String username) {
        return cartRepository.findByUserId(username);
    }
}
