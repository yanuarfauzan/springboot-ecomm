package com.yanfaisn.restapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserRequest {
    
    @NotBlank(message = "username tidak boleh kosong")
    private String username;
    @NotBlank(message = "password tidak boleh kosong")
    private String password;
}
