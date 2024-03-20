package com.awesomepizza.awesomepizzaapi.service;

import com.awesomepizza.awesomepizzaapi.dto.SignUpDTO;
import com.awesomepizza.awesomepizzaapi.model.User;
import com.awesomepizza.awesomepizzaapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByLogin(username);
    }

    public UserDetails signUp(SignUpDTO data) {//todo fix return not used
        if (repository.findByLogin(data.getLogin()) != null) {
            throw new RuntimeException("Username already exists"); //todo fix
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        User newUser = new User(data.getLogin(), encryptedPassword, data.getRole());
        return repository.save(newUser);
    }
}