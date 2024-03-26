package com.awesomepizza.awesomepizzaapi.service.impl;

import com.awesomepizza.awesomepizzaapi.dto.SignUpDTO;
import com.awesomepizza.awesomepizzaapi.exception.UsernameExistsException;
import com.awesomepizza.awesomepizzaapi.model.User;
import com.awesomepizza.awesomepizzaapi.repository.UserRepository;
import com.awesomepizza.awesomepizzaapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void signUp(SignUpDTO data) {
        if (userRepository.findByUsername(data.getUsername()) != null)
            throw new UsernameExistsException("Username already exists");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        User newUser = new User(data.getUsername(), encryptedPassword, data.getRole());
        userRepository.save(newUser);
    }
}