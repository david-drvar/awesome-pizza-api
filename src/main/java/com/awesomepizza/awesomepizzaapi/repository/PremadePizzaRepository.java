package com.awesomepizza.awesomepizzaapi.repository;

import com.awesomepizza.awesomepizzaapi.model.PremadePizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PremadePizzaRepository extends JpaRepository<PremadePizza, Long> {
}
