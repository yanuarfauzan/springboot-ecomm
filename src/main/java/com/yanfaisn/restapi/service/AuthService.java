package com.yanfaisn.restapi.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.yanfaisn.restapi.dto.TokenResponse;
import com.yanfaisn.restapi.entity.Users;
import com.yanfaisn.restapi.repository.UsersRepository;
import com.yanfaisn.restapi.security.BCrypt;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    public TokenResponse login(Users users) {
        Users user = usersRepository.findByUsername(users.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username salah"));

        if (BCrypt.checkpw(users.getPassword(), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30Days());
            usersRepository.save(user);
            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiredAt())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password salah");
        }
    }

    private Long next30Days() {
        return System.currentTimeMillis() + (1000 * 16 * 24 * 30);
    }

}
