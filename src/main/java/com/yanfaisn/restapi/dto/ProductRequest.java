package com.yanfaisn.restapi.dto;

import java.math.BigDecimal;

import com.yanfaisn.restapi.entity.Category;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequest {

    private String id;
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "description is required")
    private String description;
    @NotBlank(message = "image is required")
    private String image;
    @NotNull
    private Category category;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
    @DecimalMin(value = "0.0", inclusive = false)
    private Double stock;
}
