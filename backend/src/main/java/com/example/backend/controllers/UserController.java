package com.example.backend.controllers;

import com.example.backend.domain.User;
import com.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Map<String, Boolean> login(@RequestParam String username, @RequestParam String password) {

        try {
            User user = userService.userLogin(username, password);
            System.out.println(user);
            return Map.of("login", true);
        }
        catch (Exception e) {
            return Map.of("login", false);
        }
    }
}
