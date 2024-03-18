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
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pizza_generator")
    @SequenceGenerator(name = "pizza_generator", sequenceName = "pizza_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "premadePizza_id")
    private PremadePizza premadePizza;

    @Enumerated(EnumType.STRING)
    private PizzaSize pizzaSize;

    @ManyToMany
    @JoinTable(name = "pizza_ingredient",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> extras;

    @ManyToMany(mappedBy = "pizzaList")
    private List<Order> orders;
}
