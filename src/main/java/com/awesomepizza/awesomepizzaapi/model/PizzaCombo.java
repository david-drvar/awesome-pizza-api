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
public class PizzaCombo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "premadePizza_id")
    private PremadePizza premadePizza;

    @Enumerated(EnumType.STRING)
    private PizzaSize pizzaSize;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pizzacombo_ingredient",
            joinColumns = @JoinColumn(name = "pizzacombo_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> extras;

    @ManyToMany(mappedBy = "pizzaComboList", fetch = FetchType.LAZY)
    private List<Order> orders;

    @Column
    private Double price;
}
