package com.kb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kb.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByEmail(String email);
    
    Optional<User> findByContact(String contact);
    
    Optional<User> findByEmailAndPassword(String email, String password);
    List<User> findByRole(String role);
}
