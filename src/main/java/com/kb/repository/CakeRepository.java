package com.kb.repository;

import com.kb.Entity.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CakeRepository extends JpaRepository<Cake, Long> {
    
    List<Cake> findByIsAvailableTrue();
    
    List<Cake> findByNameContainingIgnoreCase(String name);
    
    List<Cake> findByPriceBetween(Double minPrice, Double maxPrice);
}
