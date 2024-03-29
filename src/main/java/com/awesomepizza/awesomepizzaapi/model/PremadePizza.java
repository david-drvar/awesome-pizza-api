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
public class PremadePizza {

    @Transient
    public static final Double PIZZA_BASE_PRICE = 5.99;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "premade pizza name must not be null")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "premadePizza_ingredient",
            joinColumns = @JoinColumn(name = "premadePizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredientList;

    @OneToMany(mappedBy = "premadePizza", fetch = FetchType.LAZY)
    private List<PizzaCombo> pizzaCombos;

    @NotNull(message = "premade pizza price must not be null")
    private Double price;

}
