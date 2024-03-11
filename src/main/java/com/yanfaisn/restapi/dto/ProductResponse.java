package com.yanfaisn.restapi.dto;

import java.math.BigDecimal;

import com.yanfaisn.restapi.entity.Category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private String image;
    private Category category;
    private BigDecimal price;
    private Double stock;
}
