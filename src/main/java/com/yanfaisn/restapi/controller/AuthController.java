package com.yanfaisn.restapi.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanfaisn.restapi.dto.LoginUserRequest;
import com.yanfaisn.restapi.dto.ResponseData;
import com.yanfaisn.restapi.dto.TokenResponse;
import com.yanfaisn.restapi.entity.Users;
import com.yanfaisn.restapi.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService AuthService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/user/login")
    public ResponseEntity<ResponseData<?>> login(@Valid @RequestBody LoginUserRequest loginUserRequest, Errors errors) {
        Users user = modelMapper.map(loginUserRequest, Users.class);
        TokenResponse tokenResponse = AuthService.login(user);
        ResponseData<TokenResponse> response = new ResponseData<>();
        response.setStatus(true);
        response.getMessage().add("user berhasil login");
        response.setPayload(tokenResponse);
        return ResponseEntity.ok(response);
    }
}
