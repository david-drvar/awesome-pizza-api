package com.awesomepizza.awesomepizzaapi.service.impl;

import com.awesomepizza.awesomepizzaapi.model.PizzaCombo;
import com.awesomepizza.awesomepizzaapi.repository.PizzaComboRepository;
import com.awesomepizza.awesomepizzaapi.service.PizzaComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PizzaComboServiceImpl implements PizzaComboService {

    private final PizzaComboRepository pizzaComboRepository;

    @Autowired
    public PizzaComboServiceImpl(PizzaComboRepository pizzaComboRepository) {
        this.pizzaComboRepository = pizzaComboRepository;
    }

    @Override
    public PizzaCombo save(PizzaCombo entity) {
        return this.pizzaComboRepository.save(entity);
    }

    @Override
    public Collection<PizzaCombo> read() {
        return this.pizzaComboRepository.findAll();
    }

    @Override
    public Optional<PizzaCombo> read(Long id) {
        return this.pizzaComboRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        this.pizzaComboRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.pizzaComboRepository.existsById(id);
    }
}
