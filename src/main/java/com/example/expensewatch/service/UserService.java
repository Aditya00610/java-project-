package com.example.expensewatch.service;

import com.example.expensewatch.dto.UserDTO;
import com.example.expensewatch.entity.User;
import com.example.expensewatch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDTO createUser(String username, String rawPassword, String role) {
        User u = User.builder().username(username).password(passwordEncoder.encode(rawPassword)).role(role).build();
        User saved = userRepository.save(u);
        return UserDTO.builder().id(saved.getId()).username(saved.getUsername()).role(saved.getRole()).build();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
