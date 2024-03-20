package com.awesomepizza.awesomepizzaapi.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @NotEmpty(message = "town must not be empty")
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[a-zA-Z-' ]+$", message = "town can contain only letters, ' and -")
    private String town;

    @NotEmpty(message = "street must not be empty")
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-'.]+$", message = "street can contain only letters, numbers ' and -")
    private String street;

    @NotEmpty(message = "zip must not be empty")
    @Pattern(regexp = "^\\d{5}$", message = "zip code must have 5 numbers")
    private String zip;
}
