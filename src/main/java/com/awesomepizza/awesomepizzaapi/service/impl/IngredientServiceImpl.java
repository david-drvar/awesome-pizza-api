package com.awesomepizza.awesomepizzaapi.service.impl;

import com.awesomepizza.awesomepizzaapi.model.Ingredient;
import com.awesomepizza.awesomepizzaapi.model.IngredientPriceHistory;
import com.awesomepizza.awesomepizzaapi.model.PremadePizza;
import com.awesomepizza.awesomepizzaapi.repository.IngredientRepository;
import com.awesomepizza.awesomepizzaapi.service.IngredientPriceHistoryService;
import com.awesomepizza.awesomepizzaapi.service.IngredientService;
import com.awesomepizza.awesomepizzaapi.service.PremadePizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    private final IngredientPriceHistoryService ingredientPriceHistoryService;
    private final PremadePizzaService premadePizzaService;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientPriceHistoryService ingredientPriceHistoryService, PremadePizzaService premadePizzaService) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientPriceHistoryService = ingredientPriceHistoryService;
        this.premadePizzaService = premadePizzaService;
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

    @Override
    @Scheduled(cron = "0 0 3 * * *") // runs at 3AM every night
//    @Scheduled(fixedDelay = 30000) // runs every 30 seconds
    @Transactional
    public void updatePrices() {
        List<IngredientPriceHistory> ingredientPriceHistoryList = (List<IngredientPriceHistory>) this.ingredientPriceHistoryService.getIngredientPriceHistoryByDate(LocalDate.now());
        if (ingredientPriceHistoryList.isEmpty())
            return;

        Set<PremadePizza> premadePizzaSet = new HashSet<>();

        for (IngredientPriceHistory ingredientPriceHistory : ingredientPriceHistoryList) {
            Ingredient ingredient = ingredientPriceHistory.getIngredient();
            ingredient.setPrice(ingredientPriceHistory.getPrice());
            this.ingredientRepository.save(ingredient);

            Collection<PremadePizza> premadePizzaList = this.premadePizzaService.getAllContainingIngredient(ingredient);
            premadePizzaSet.addAll(premadePizzaList);
        }

        if (premadePizzaSet.isEmpty())
            return;

        for (PremadePizza premadePizza : premadePizzaSet) {
            Double newPremadePizzaPrice = calculateNewPremadePizzaPrice(premadePizza);
            premadePizza.setPrice(newPremadePizzaPrice);
            this.premadePizzaService.save(premadePizza);
        }
    }

    private Double calculateNewPremadePizzaPrice(PremadePizza premadePizza) {
        Double price = PremadePizza.PIZZA_BASE_PRICE;
        for (Ingredient ingredient : premadePizza.getIngredientList()) {
            price += ingredient.getPrice();
        }

        return price;
    }
}
