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
    public Cake updateCake(Long cakeId, Cake cake) {
        return cakeRepository.findById(cakeId).map(existingCake -> {
            existingCake.setName(cake.getName());
            existingCake.setPrice(cake.getPrice());
            existingCake.setIsAvailable(cake.getIsAvailable());
            existingCake.setDescription(cake.getDescription());
            existingCake.setPhoto(cake.getPhoto());
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
