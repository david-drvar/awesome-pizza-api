package com.awesomepizza.awesomepizzaapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PizzaSize {
    SMALL(1.0),
    MEDIUM(1.5),
    LARGE(2);

    private final double priceMultiplier;
}