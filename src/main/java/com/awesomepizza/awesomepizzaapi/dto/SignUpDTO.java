package com.awesomepizza.awesomepizzaapi.dto;

import com.awesomepizza.awesomepizzaapi.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
    private String login;
    private String password;
    private UserRole role;
}
