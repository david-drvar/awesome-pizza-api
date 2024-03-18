package com.awesomepizza.awesomepizzaapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class IngredientPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredientpricehistory_generator")
    @SequenceGenerator(name = "ingredientpricehistory_generator", sequenceName = "ingredientpricehistory_seq", allocationSize = 1)
    private Long id;

    @Column
    private Double price;

    @Column
    private LocalDateTime dateStart;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

}
