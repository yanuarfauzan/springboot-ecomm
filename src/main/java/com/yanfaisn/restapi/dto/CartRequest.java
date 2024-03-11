package com.yanfaisn.restapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartRequest {

    private String productId;
    private Double quantity;
}
