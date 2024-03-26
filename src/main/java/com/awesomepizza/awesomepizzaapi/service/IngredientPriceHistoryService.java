package com.awesomepizza.awesomepizzaapi.service;

import com.awesomepizza.awesomepizzaapi.model.IngredientPriceHistory;

import java.time.LocalDate;
import java.util.Collection;

public interface IngredientPriceHistoryService extends CRUDService<IngredientPriceHistory> {

    Collection<IngredientPriceHistory> getIngredientPriceHistoryByDate(LocalDate localDate);

}
