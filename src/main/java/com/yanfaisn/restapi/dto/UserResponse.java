package com.yanfaisn.restapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private String username;
    private String address;
    private String email;
    private String number_telphone;
    private String roles;
    private Boolean is_active;
}
