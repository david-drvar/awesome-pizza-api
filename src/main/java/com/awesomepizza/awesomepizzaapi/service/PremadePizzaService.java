package com.awesomepizza.awesomepizzaapi.service;

import com.awesomepizza.awesomepizzaapi.model.Ingredient;
import com.awesomepizza.awesomepizzaapi.model.PremadePizza;

import java.util.Collection;

public interface PremadePizzaService extends CRUDService<PremadePizza> {

    Collection<PremadePizza> getAllContainingIngredient(Ingredient ingredient);


}
