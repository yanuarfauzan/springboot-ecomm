package com.yanfaisn.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yanfaisn.restapi.entity.ItemOrder;

public interface ItemOrderRepository extends JpaRepository<ItemOrder, String>{
    
}
