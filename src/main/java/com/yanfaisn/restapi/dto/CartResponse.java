package com.yanfaisn.restapi.dto;

import java.math.BigDecimal;

import com.yanfaisn.restapi.entity.Product;
import com.yanfaisn.restapi.entity.Users;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartResponse {
    private Users user;
    private Product product;
    private Double quantity;
    private BigDecimal price;
    private BigDecimal amount;
}
