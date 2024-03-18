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
public class PremadePizza {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "premadepizza_generator")
    @SequenceGenerator(name = "premadepizza_generator", sequenceName = "premadepizza_seq", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(name = "premadePizza_ingredient",
            joinColumns = @JoinColumn(name = "premadePizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredientList;

    @OneToMany(mappedBy = "premadePizza")
    private List<Pizza> pizzas;

}
