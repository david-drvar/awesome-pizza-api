package com.awesomepizza.awesomepizzaapi.service.impl;

import com.awesomepizza.awesomepizzaapi.model.Ingredient;
import com.awesomepizza.awesomepizzaapi.model.PremadePizza;
import com.awesomepizza.awesomepizzaapi.repository.PremadePizzaRepository;
import com.awesomepizza.awesomepizzaapi.service.PremadePizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PremadePizzaServiceImpl implements PremadePizzaService {

    private final PremadePizzaRepository premadePizzaRepository;

    @Autowired
    public PremadePizzaServiceImpl(PremadePizzaRepository premadePizzaRepository) {
        this.premadePizzaRepository = premadePizzaRepository;
    }


    @Override
    public PremadePizza save(PremadePizza entity) {
        return this.premadePizzaRepository.save(entity);
    }

    @Override
    public Collection<PremadePizza> read() {
        return this.premadePizzaRepository.findAll();
    }

    @Override
    public Optional<PremadePizza> read(Long id) {
        return this.premadePizzaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        this.premadePizzaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.premadePizzaRepository.existsById(id);
    }

    @Override
    public Collection<PremadePizza> getAllContainingIngredient(Ingredient ingredient) {
        return this.premadePizzaRepository.findAllByIngredientListContainingIgnoreCase(ingredient);
    }
}
