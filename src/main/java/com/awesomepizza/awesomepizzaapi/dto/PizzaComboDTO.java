package com.awesomepizza.awesomepizzaapi.dto;

import com.awesomepizza.awesomepizzaapi.model.enums.PizzaSize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

    private Long id;

    @Valid
    @NotNull(message = "premade pizza must not be empty")
    private PremadePizzaDTO premadePizza;

    @NotNull(message = "pizza size must not be null")
    private PizzaSize pizzaSize;

    @Valid
    private List<IngredientDTO> extras;

    private Double price;
}
