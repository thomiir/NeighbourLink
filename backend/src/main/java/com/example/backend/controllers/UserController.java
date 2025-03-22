package com.example.backend.controllers;

import com.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public long getUsers() {
        long size = StreamSupport.stream(userService.getUsers().spliterator(), false).count();
        System.out.println(size);
        return size;
    }
}
