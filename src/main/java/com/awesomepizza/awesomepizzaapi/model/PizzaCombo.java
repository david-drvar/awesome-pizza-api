package com.awesomepizza.awesomepizzaapi.model;

import com.awesomepizza.awesomepizzaapi.model.enums.PizzaSize;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

    @Valid
    @NotNull(message = "premade pizza must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "premadePizza_id")
    private PremadePizza premadePizza;

    @NotNull(message = "pizza size must not be null")
    @Enumerated(EnumType.STRING)
    private PizzaSize pizzaSize;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pizzacombo_ingredient",
            joinColumns = @JoinColumn(name = "pizzacombo_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> extras;

    @ManyToMany(mappedBy = "pizzaComboList", fetch = FetchType.LAZY)
    private List<Order> orders;

    @NotNull(message = "pizza combo price must not be null")
    private Double price;
}
