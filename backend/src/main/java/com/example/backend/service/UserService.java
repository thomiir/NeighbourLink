package com.example.backend.service;

import com.example.backend.domain.User;
import com.example.backend.repository.IUserRepository;
import com.example.backend.service.exceptions.ServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository repository) {
        this.userRepository = repository;
    }

    public Optional<User> findUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User userLogin(String username, String password) {
        Optional<User> userOptional = findUser(username);
        if (userOptional.isEmpty())
            throw new ServiceException("That user doesn't exist!");
        User user = userOptional.get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword()))
            throw new ServiceException("Wrong password!");
        return user;
    }
}