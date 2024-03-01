package com.prodonik.posting.controllers;

import com.prodonik.posting.models.AuthenticationResponse;
import com.prodonik.posting.models.User;
import com.prodonik.posting.services.AuthenticationService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
            ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/users")
    public List<User> getAllUsers () {
        System.out.println("Came to get users");
        return this.authService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public Optional<User> findUserById (@PathVariable UUID id) {
        return this.authService.findById(id);
    }
}
