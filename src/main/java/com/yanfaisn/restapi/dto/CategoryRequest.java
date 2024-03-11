package com.yanfaisn.restapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryRequest {
    private String id;
    @NotBlank(message = "name tidak boleh kosong")
    private String name;
}
