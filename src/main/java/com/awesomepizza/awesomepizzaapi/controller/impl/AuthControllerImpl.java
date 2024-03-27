package com.awesomepizza.awesomepizzaapi.controller.impl;

import com.awesomepizza.awesomepizzaapi.config.TokenProvider;
import com.awesomepizza.awesomepizzaapi.controller.AuthController;
import com.awesomepizza.awesomepizzaapi.dto.JwtDTO;
import com.awesomepizza.awesomepizzaapi.dto.SignInDTO;
import com.awesomepizza.awesomepizzaapi.dto.SignUpDTO;
import com.awesomepizza.awesomepizzaapi.model.User;
import com.awesomepizza.awesomepizzaapi.service.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@PermitAll
public class AuthControllerImpl implements AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @Autowired
    public AuthControllerImpl(AuthenticationManager authenticationManager, AuthService authService, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpDTO data) {
        authService.signUp(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PostMapping("/signin")
    public ResponseEntity<JwtDTO> signIn(@RequestBody @Valid SignInDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        Authentication authUser = authenticationManager.authenticate(usernamePassword);
        String accessToken = tokenProvider.generateAccessToken((User) authUser.getPrincipal());
        return new ResponseEntity<>(new JwtDTO(accessToken), HttpStatus.OK);
    }
}