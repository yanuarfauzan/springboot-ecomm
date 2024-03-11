package com.yanfaisn.restapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "name tidak boleh kosong")
    private String username;
    @NotBlank(message = "email tidak boleh kosong")
    @Email(message = "format email tidak benar")
    private String email;
    private String password;
    @NotBlank(message = "password confirmation tidak boleh kosong")
    private String reTypePassword;
    @NotBlank
    private String role;
}
