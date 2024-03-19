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

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
    private List<IngredientPriceHistory> ingredientPriceHistory;

    @ManyToMany(mappedBy = "extras", fetch = FetchType.LAZY)
    private List<PizzaCombo> pizzaCombos;

    @ManyToMany(mappedBy = "ingredientList", fetch = FetchType.LAZY)
    private List<PremadePizza> premadePizzas;
}
