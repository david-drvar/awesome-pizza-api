package com.awesomepizza.awesomepizzaapi.service.impl;

import com.awesomepizza.awesomepizzaapi.dto.SignUpDTO;
import com.awesomepizza.awesomepizzaapi.exception.UsernameExistsException;
import com.awesomepizza.awesomepizzaapi.model.User;
import com.awesomepizza.awesomepizzaapi.repository.UserRepository;
import com.awesomepizza.awesomepizzaapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void signUp(SignUpDTO data) {
        if (userRepository.findByUsername(data.getUsername()) != null)
            throw new UsernameExistsException("Username already exists");

        String encryptedPassword = passwordEncoder.encode(data.getPassword());
        User newUser = new User(data.getUsername(), encryptedPassword, data.getRole());
        userRepository.save(newUser);
    }
}