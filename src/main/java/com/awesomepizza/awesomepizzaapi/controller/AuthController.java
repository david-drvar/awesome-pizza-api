package com.awesomepizza.awesomepizzaapi.controller;

import com.awesomepizza.awesomepizzaapi.dto.JwtDTO;
import com.awesomepizza.awesomepizzaapi.dto.SignInDTO;
import com.awesomepizza.awesomepizzaapi.dto.SignUpDTO;
import org.springframework.http.ResponseEntity;

public interface AuthController {

    ResponseEntity<Void> signUp(SignUpDTO data);

    ResponseEntity<JwtDTO> signIn(SignInDTO data);

}
