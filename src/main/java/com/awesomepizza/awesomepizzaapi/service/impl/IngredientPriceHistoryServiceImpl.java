package com.awesomepizza.awesomepizzaapi.service.impl;

import com.awesomepizza.awesomepizzaapi.model.IngredientPriceHistory;
import com.awesomepizza.awesomepizzaapi.repository.IngredientPriceHistoryRepository;
import com.awesomepizza.awesomepizzaapi.service.IngredientPriceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Service
public class IngredientPriceHistoryServiceImpl implements IngredientPriceHistoryService {

    private final IngredientPriceHistoryRepository ingredientPriceHistoryRepository;

    @Autowired
    public IngredientPriceHistoryServiceImpl(IngredientPriceHistoryRepository ingredientPriceHistoryRepository) {
        this.ingredientPriceHistoryRepository = ingredientPriceHistoryRepository;
    }

    @Override
    public IngredientPriceHistory save(IngredientPriceHistory entity) {
        return this.ingredientPriceHistoryRepository.save(entity);
    }

    @Override
    public Collection<IngredientPriceHistory> read() {
        return this.ingredientPriceHistoryRepository.findAll();
    }

    @Override
    public Optional<IngredientPriceHistory> read(Long id) {
        return this.ingredientPriceHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        this.ingredientPriceHistoryRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.ingredientPriceHistoryRepository.existsById(id);
    }

    @Override
    public Collection<IngredientPriceHistory> getIngredientPriceHistoryByDate(LocalDate localDate) {
        return this.ingredientPriceHistoryRepository.findAllByDateStartEquals(localDate);
    }
}
