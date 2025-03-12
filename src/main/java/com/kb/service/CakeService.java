package com.kb.service;

import com.kb.Entity.Cake;
import java.util.List;
import java.util.Optional;

public interface CakeService {
    Cake addCake(Cake cake);
    Cake updateCake(Long cakeId, Optional<Cake> c);
    void deleteCake(Long cakeId);
    Optional<Cake> getCakeById(Long cakeId);
    List<Cake> getAllCakes();
    List<Cake> getAvailableCakes();
    List<Cake> searchCakesByName(String name);
    List<Cake> filterCakesByPrice(Double minPrice, Double maxPrice);
}
