package com.awesomepizza.awesomepizzaapi.service.impl;

import com.awesomepizza.awesomepizzaapi.model.Ingredient;
import com.awesomepizza.awesomepizzaapi.repository.IngredientRepository;
import com.awesomepizza.awesomepizzaapi.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient save(Ingredient entity) {
        return this.ingredientRepository.save(entity);
    }

    @Override
    public Collection<Ingredient> read() {
        return this.ingredientRepository.findAll();
    }

    @Override
    public Optional<Ingredient> read(Long id) {
        return this.ingredientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        this.ingredientRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.ingredientRepository.existsById(id);
    }
}
