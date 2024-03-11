package com.yanfaisn.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yanfaisn.restapi.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, String> {
    
}
