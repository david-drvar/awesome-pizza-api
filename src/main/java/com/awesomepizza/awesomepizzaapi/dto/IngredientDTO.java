package com.awesomepizza.awesomepizzaapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {

    @NotNull(message = "ingredient id must not be null")
    private Long id;

    private String name;
}
