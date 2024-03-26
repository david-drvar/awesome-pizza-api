package com.awesomepizza.awesomepizzaapi.repository;

import com.awesomepizza.awesomepizzaapi.model.IngredientPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface IngredientPriceHistoryRepository extends JpaRepository<IngredientPriceHistory, Long> {

    Collection<IngredientPriceHistory> findAllByDateStartEquals(LocalDate date);
}
