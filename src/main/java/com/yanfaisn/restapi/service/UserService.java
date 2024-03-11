package com.yanfaisn.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.yanfaisn.restapi.entity.Users;
import com.yanfaisn.restapi.exception.ResourceNotFoundException;
import com.yanfaisn.restapi.repository.UsersRepository;
import com.yanfaisn.restapi.security.BCrypt;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Users findById(String id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User dengan " + id + " tersebut tidak tersedia"));
    }

    public Users create(Users users) {
        return usersRepository.save(users);
    }

    public Users register(Users users) {
        if (usersRepository.existsByUsername(users.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username sudah terdaftar");
        } else if (usersRepository.existsByEmail(users.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email sudah terdaftar");
        }
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return usersRepository.save(users);
    }

    public Users edit(Users users) {
        return usersRepository.save(users);
    }

    public void deleteById(String id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = usersRepository.findByUsername(username);
        var userObj = user.get();
        if (user.isPresent()) {
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }

    }

    public String[] getRoles(Users user) {
        if (user.getRole() == null) {
            return new String[] { "USER" };
        }

        return user.getRole().split(",");
    }
}
