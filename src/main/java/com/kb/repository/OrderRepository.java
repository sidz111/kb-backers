package com.kb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kb.Entity.Order;
import com.kb.Entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByUser(User user);
    
    List<Order> findByCakeId(Long cakeId);
    
    List<Order> findByTimeContaining(String time);
}
