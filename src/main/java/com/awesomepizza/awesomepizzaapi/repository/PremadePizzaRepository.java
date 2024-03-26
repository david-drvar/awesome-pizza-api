package com.awesomepizza.awesomepizzaapi.repository;

import com.awesomepizza.awesomepizzaapi.model.Ingredient;
import com.awesomepizza.awesomepizzaapi.model.PremadePizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PremadePizzaRepository extends JpaRepository<PremadePizza, Long> {

    Collection<PremadePizza> findAllByIngredientListContainingIgnoreCase(Ingredient ingredient);

}
