package com.awesomepizza.awesomepizzaapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN("admin"),
    PIZZAMAKER("pizzamaker");

    private final String role;
}