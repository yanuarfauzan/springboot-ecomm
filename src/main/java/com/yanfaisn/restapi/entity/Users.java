package com.yanfaisn.restapi.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users{

    @Id
    private String id;
    @Column(unique = true)
    private String username;
    private String password;
    private String address;
    private String email;
    private String number_telphone;
    private String token;
    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;
    private String role;
    private Boolean is_active;

    public Users(String email, String password) {
        this.password = password;
        this.email = email;
    }

    public Users(String username) {
        this.id = username;
    }
}
