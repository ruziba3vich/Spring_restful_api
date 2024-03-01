package com.prodonik.posting.services;

import com.prodonik.posting.models.AuthenticationResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.prodonik.posting.models.Token;
import com.prodonik.posting.repositories.TokenRepository;
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
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register (User request) {
        User user = new User(
                request.getFirstname(),
                request.getLastname(),
                request.getUsername(),
                request.getBirthDate(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole(),
                request.getTokens(),
                request.getPosts()
        );
        String jwt = jwtService.generateToken(userRepository.save(user));
        saveUserToken(jwt, user);
        return new AuthenticationResponse(jwt);
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
        saveUserToken(token, user);
        return new AuthenticationResponse(token);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return this.userRepository.findById(id);
    }

    private void saveUserToken (String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
