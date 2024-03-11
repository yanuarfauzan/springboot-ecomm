package com.yanfaisn.restapi.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    private String id;
    @NotBlank(message = "nama tidak boleh kosong")
    private String name;
    private String description;
    private String image;
    @JoinColumn
    @ManyToOne
    private Category category;
    private BigDecimal price;
    private Double stock;
}
