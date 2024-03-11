package com.yanfaisn.restapi.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanfaisn.restapi.dto.ResponseData;
import com.yanfaisn.restapi.dto.UserRequest;
import com.yanfaisn.restapi.dto.UserResponse;
import com.yanfaisn.restapi.entity.Users;
import com.yanfaisn.restapi.service.UserService;
import com.yanfaisn.restapi.utility.ErrorParsingUtility;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/user")
    public ResponseEntity<ResponseData<List<UserResponse>>> findAll() {
        ResponseData<List<UserResponse>> response = new ResponseData<>();
        List<Users> users = userService.findAll();
        List<UserResponse> userResponses = users.stream().map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
        response.setStatus(true);
        response.getMessage().add("berhasil menampilkan users");
        response.setPayload(userResponses);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseData<?>> create(@Valid @RequestBody UserRequest userRequest, Errors errors) {
        ResponseData<UserResponse> response = new ResponseData<>();
        if (errors.hasErrors()) {
            response.setStatus(false);
            response.setMessage(ErrorParsingUtility.parse(errors));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            Users users = modelMapper.map(userRequest, Users.class);
            users.setId(UUID.randomUUID().toString());
            userService.create(users);
            response.setStatus(true);
            response.getMessage().add("berhasil manambahkan user");
            response.setPayload(modelMapper.map(users, UserResponse.class));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(false);
            response.getMessage().add(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseData<?>> findById(@PathVariable("id") String id) {
        ResponseData<UserResponse> response = new ResponseData<>();
        Users user = userService.findById(id);
        response.setStatus(true);
        response.getMessage().add("berhasil menampilkan satu user");
        response.setPayload(modelMapper.map(user, UserResponse.class));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/user")
    public ResponseEntity<ResponseData<?>> edit(@Valid @RequestBody UserRequest userRequest, Errors errors) {
        ResponseData<UserResponse> response = new ResponseData<>();

        if (errors.hasErrors()) {
            response.setStatus(false);
            response.setMessage(ErrorParsingUtility.parse(errors));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            Users user = modelMapper.map(userRequest, Users.class);
            userService.edit(user);
            response.setStatus(true);
            response.getMessage().add("berhasil mengedit user");
            response.setPayload(modelMapper.map(user, UserResponse.class));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(false);
            response.getMessage().add("gagal mengedit user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/user/{id}")
    public void deleteById(@PathVariable("id") String id) {
        userService.deleteById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData<?>> registerUser(@Valid @RequestBody UserRequest userRequest, Errors errors) {
        ResponseData<Users> response = new ResponseData<>();
        Users user = modelMapper.map(userRequest, Users.class);
        user.setId(UUID.randomUUID().toString());
        response.setStatus(true);
        response.getMessage().add("berhasil register");
        response.setPayload(userService.register(user));
        return ResponseEntity.ok(response);
    }

}
