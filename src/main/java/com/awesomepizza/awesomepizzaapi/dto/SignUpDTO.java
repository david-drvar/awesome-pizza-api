package com.awesomepizza.awesomepizzaapi.dto;

import com.awesomepizza.awesomepizzaapi.model.enums.UserRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {

    @NotEmpty(message = "login must not be empty")
    private String login;

    @NotEmpty(message = "password must not be empty")
    private String password;

    @NotNull(message = "role must not be null")
    private UserRole role;
}
