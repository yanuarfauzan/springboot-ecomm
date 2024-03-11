package com.yanfaisn.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yanfaisn.restapi.entity.LogOrder;

public interface LogOrderRepository extends JpaRepository<LogOrder, String> {
    
}
