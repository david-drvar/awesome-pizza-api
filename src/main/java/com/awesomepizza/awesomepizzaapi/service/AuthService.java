package com.awesomepizza.awesomepizzaapi.service;

import com.awesomepizza.awesomepizzaapi.dto.SignUpDTO;
import com.awesomepizza.awesomepizzaapi.exception.UsernameExistsException;
import com.awesomepizza.awesomepizzaapi.model.User;
import com.awesomepizza.awesomepizzaapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    UserRepository userRepository;
    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByLogin(username);
    }

    public void signUp(SignUpDTO data) {
        if (userRepository.findByLogin(data.getLogin()) != null)
            throw new UsernameExistsException("Username already exists");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        User newUser = new User(data.getLogin(), encryptedPassword, data.getRole());
        userRepository.save(newUser);
    }
}