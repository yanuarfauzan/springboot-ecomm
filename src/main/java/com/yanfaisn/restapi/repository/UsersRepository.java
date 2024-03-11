package com.yanfaisn.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yanfaisn.restapi.entity.Users;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {

    

    // method yang bakal di cek oleh sping security
    Optional<Users> findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // Penggunaan optional digunakan agar bisa menerapkan orElseThrow di service nya
    Optional<Users> findByUsername(String username);
}
