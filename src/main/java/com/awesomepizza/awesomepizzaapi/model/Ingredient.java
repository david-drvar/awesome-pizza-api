package com.awesomepizza.awesomepizzaapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "ingredient name must not be empty")
    private String name;

    @NotEmpty(message = "ingredient's price history must not be empty")
    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
    private List<IngredientPriceHistory> ingredientPriceHistory;

    @ManyToMany(mappedBy = "extras", fetch = FetchType.LAZY)
    private List<PizzaCombo> pizzaCombos;

    @ManyToMany(mappedBy = "ingredientList", fetch = FetchType.LAZY)
    private List<PremadePizza> premadePizzas;

    @NotNull(message = "ingredient's price must not be null")
    private Double price;
}
