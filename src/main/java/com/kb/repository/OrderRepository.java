package com.kb.repository;

import com.kb.Entity.Order;
import com.kb.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByCakeId(Long cakeId);
    
    List<Order> findByIsConfirm(Boolean isConfirm);

    @Query("SELECT o FROM Order o WHERE (:time IS NULL OR o.time LIKE %:time%)")
    List<Order> findByTimeContaining(@Param("time") String time);
}
