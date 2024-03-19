package com.awesomepizza.awesomepizzaapi.repository;

import com.awesomepizza.awesomepizzaapi.model.IngredientPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientPriceHistoryRepository extends JpaRepository<IngredientPriceHistory, Long> {
}
