package com.prodonik.posting.services;

import com.prodonik.posting.models.AuthenticationResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prodonik.posting.models.User;
import com.prodonik.posting.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register (User request) {

        return new AuthenticationResponse(jwtService
                        .generateToken(userRepository.save(
                            new User(
                                request.getFirstname(),
                                request.getLastname(),
                                request.getUsername(),
                                request.getBirthDate(),
                                passwordEncoder.encode(request.getPassword()),
                                request.getRole()
                            )
                        )));
    }

    public AuthenticationResponse authenticate (User request) {
        this.authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
            )
        );

        User user = this.userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = this.jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
