package com.javamessaging.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import com.javamessaging.model.User;
import com.javamessaging.service.RedisService;
import com.javamessaging.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        if (userService.getUser(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A user with this email already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        redisService.setUserOnline(user.getId());
        return userService.registerUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable String id) {
        return userService.getUser(id);
    }
    
    @PostMapping("/login")
    public User authenticate(@RequestBody Map<String, String> request) {
        Optional<User> u = userService.getUser(request.get("email"));
        if (!u.isPresent()) {
            throw new IllegalArgumentException("A user with this email does not exist.");
        }
        if (passwordEncoder.matches(u.get().getPassword(), request.get("password"))) {
            return u.get();
        }
        redisService.setUserOnline(request.get("id"));
        throw new IllegalArgumentException("Incorrect password");
    }

    @PostMapping("/logout")
    public void logoout(@RequestBody Map<String, String> request) {
        redisService.setUserOffline(request.get("id"));
    }
    
}
