package com.prodonik.posting.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prodonik.posting.models.AuthenticationResponse;
import com.prodonik.posting.models.User;
import com.prodonik.posting.services.AuthenticationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
// @RequestMapping("/api/")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
        @RequestBody User request
    ) {
        return ResponseEntity.ok(this.authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (
        @RequestBody User request
    ) {
        return ResponseEntity.ok(this.authenticationService.authenticate(request));
    }
}
