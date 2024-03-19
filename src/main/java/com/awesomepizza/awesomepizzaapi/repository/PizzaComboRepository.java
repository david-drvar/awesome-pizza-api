package com.awesomepizza.awesomepizzaapi.repository;

import com.awesomepizza.awesomepizzaapi.model.PizzaCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaComboRepository extends JpaRepository<PizzaCombo, Long> {
}
