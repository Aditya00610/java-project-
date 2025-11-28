package com.example.expensewatch.controller;

import com.example.expensewatch.dto.UserDTO;
import com.example.expensewatch.entity.User;
import com.example.expensewatch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = userService.findByUsername(username).orElseThrow();
        return ResponseEntity.ok(new UserDTO(u.getId(), u.getUsername(), u.getRole()));
    }
}
