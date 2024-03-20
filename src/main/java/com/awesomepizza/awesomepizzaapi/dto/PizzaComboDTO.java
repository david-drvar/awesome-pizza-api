package com.awesomepizza.awesomepizzaapi.dto;

import com.awesomepizza.awesomepizzaapi.model.enums.PizzaSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PizzaComboDTO {
    private PremadePizzaDTO premadePizza;
    private PizzaSize pizzaSize;
    private List<IngredientDTO> extras;
    private Double price;
}
