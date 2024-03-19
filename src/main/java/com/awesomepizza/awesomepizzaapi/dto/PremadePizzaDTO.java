package com.awesomepizza.awesomepizzaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PremadePizzaDTO {
    private Long id;
    private String name;
    private List<IngredientDTO> ingredientList;
    private Double price;
}
