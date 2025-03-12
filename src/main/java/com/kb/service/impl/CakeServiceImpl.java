package com.kb.service.impl;

import com.kb.Entity.Cake;
import com.kb.repository.CakeRepository;
import com.kb.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CakeServiceImpl implements CakeService {

    @Autowired
    private CakeRepository cakeRepository;

    @Override
    public Cake addCake(Cake cake) {
        return cakeRepository.save(cake);
    }

    @Override
    public Cake updateCake(Long cakeId, Optional<Cake> cake) {
        return cakeRepository.findById(cakeId).map(existingCake -> {
            existingCake.setName(cake.get().getName());
            existingCake.setPrice(cake.get().getPrice());
            existingCake.setIsAvailable(cake.get().getIsAvailable());
            existingCake.setDescription(cake.get().getDescription());
            existingCake.setPhoto(cake.get().getPhoto());
            return cakeRepository.save(existingCake);
        }).orElse(null);
    }

    @Override
    public void deleteCake(Long cakeId) {
        cakeRepository.deleteById(cakeId);
    }

    @Override
    public Optional<Cake> getCakeById(Long cakeId) {
        return cakeRepository.findById(cakeId);
    }

    @Override
    public List<Cake> getAllCakes() {
        return cakeRepository.findAll();
    }

    @Override
    public List<Cake> getAvailableCakes() {
        return cakeRepository.findByIsAvailableTrue();
    }

    @Override
    public List<Cake> searchCakesByName(String name) {
        return cakeRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Cake> filterCakesByPrice(Double minPrice, Double maxPrice) {
        return cakeRepository.findByPriceBetween(minPrice, maxPrice);
    }
}
