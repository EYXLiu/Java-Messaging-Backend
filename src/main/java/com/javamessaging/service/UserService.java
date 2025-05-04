package com.javamessaging.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javamessaging.model.User;
import com.javamessaging.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User Already Exists");
        }
        return userRepository.save(user);
    }

    public Optional<User> getUser(String id) {
        return userRepository.findByEmail(id).or(() -> userRepository.findByUsername(id));
    }
}
