package com.awesomepizza.awesomepizzaapi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInDTO {

    @NotEmpty(message = "login must not be empty")
    private String login;

    @NotEmpty(message = "password must not be empty")
    private String password;
}
