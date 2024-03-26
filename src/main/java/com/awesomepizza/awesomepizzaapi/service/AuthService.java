package com.awesomepizza.awesomepizzaapi.service;

import com.awesomepizza.awesomepizzaapi.dto.SignUpDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    void signUp(SignUpDTO data);
}
