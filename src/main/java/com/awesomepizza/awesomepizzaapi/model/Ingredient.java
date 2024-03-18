package com.awesomepizza.awesomepizzaapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_generator")
    @SequenceGenerator(name = "ingredient_generator", sequenceName = "ingredient_seq", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "ingredient")
    private List<IngredientPriceHistory> ingredientPriceHistory;

    @ManyToMany(mappedBy = "extras")
    private List<Pizza> pizzas;

    @ManyToMany(mappedBy = "ingredientList")
    private List<PremadePizza> premadePizzas;
}
