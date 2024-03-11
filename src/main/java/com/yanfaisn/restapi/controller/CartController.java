package com.yanfaisn.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanfaisn.restapi.dto.CartRequest;
import com.yanfaisn.restapi.dto.CartResponse;
import com.yanfaisn.restapi.dto.ResponseData;
import com.yanfaisn.restapi.entity.Cart;
import com.yanfaisn.restapi.service.CartSevice;
import com.yanfaisn.restapi.utility.ErrorParsingUtility;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartSevice cartSevice;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/user/cart/{username}")
    public ResponseEntity<ResponseData<List<CartResponse>>> findByUserId(@PathVariable("username") String username) {
        ResponseData<List<CartResponse>> response = new ResponseData<>();
        List<Cart> carts = cartSevice.findByUserId(username);
        List<CartResponse> cartResponses = carts.stream().map(cart -> modelMapper.map(cart, CartResponse.class))
                .collect(Collectors.toList());
        response.setStatus(true);
        response.getMessage().add("berhasil menampilkan data keranjang user");
        response.setPayload(cartResponses);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/cart/{id}")
    public ResponseEntity<ResponseData<?>> create(@Valid @RequestBody CartRequest cartRequest,
            @PathVariable("id") String id, Errors errors) {
        ResponseData<CartResponse> response = new ResponseData<>();
        if (errors.hasErrors()) {
            response.setStatus(false);
            response.setMessage(ErrorParsingUtility.parse(errors));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            Cart cart = modelMapper.map(cartRequest, Cart.class);
            cartSevice.create(id, cartRequest.getProductId(), cartRequest.getQuantity());
            response.setStatus(true);
            response.getMessage().add("berhasil menambahkan produk ke keranjang");
            response.setPayload(modelMapper.map(cart, CartResponse.class));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(false);
            response.getMessage().add(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/user/cart/{idUser}")
    public ResponseEntity<ResponseData<?>> updateQuantity(
            @Valid @RequestBody CartRequest cartRequest,
            Errors errors,
            @PathVariable("idUser") String IdUser) {
        ResponseData<CartResponse> response = new ResponseData<>();
        if (errors.hasErrors()) {
            response.setStatus(false);
            response.setMessage(ErrorParsingUtility.parse(errors));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            Cart cart = modelMapper.map(cartRequest, Cart.class);
            cartSevice.updateQuantity(IdUser, cartRequest.getProductId(), cartRequest.getQuantity());
            response.setStatus(true);
            response.getMessage().add("berhasil update jumlah produk");
            response.setPayload(modelMapper.map(cart, CartResponse.class));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(false);
            response.getMessage().add("gagal update jumlah produk");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/user/cart/delete/{username}/{productId}")
    public void delete(@PathVariable("username") String username, @PathVariable("productId") String productId) {
        cartSevice.delete(username, productId);
    }

}
