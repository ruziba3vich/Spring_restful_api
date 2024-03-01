package com.prodonik.posting.services;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.prodonik.posting.models.User;
import com.prodonik.posting.repositories.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    private final String secretKey = "1cc84510bee3ea62b2effaf13126398659e12e4ee7ed72cfc76fd914a141d5ac";
    private final int days = 10;
    private final UserRepository userRepository;

    public JwtService (UserRepository userRepository) { this.userRepository = userRepository; }

    public <T> T extractClaims (String token, Function<Claims, T> resolver) {
        return resolver
                .apply(
                    extractAllClaims(token)
                );
    }


    public User extractUser (String token) {
        try {
            String username = this.extractUsername(token);
            return this.userRepository
                        .findByUsername(username)
                        .get(); 
        } catch (Exception ex) {
            throw new AuthenticationServiceException("Invalid token", ex);
        }
    }

    public String extractUsername (String token) {
        return this.extractClaims(token, Claims::getSubject);
    }


    public boolean isValid (String token, UserDetails user) {
        return this.extractUsername(token)
                .equals(user.getUsername()) && 
                this.isTokenExpired(token);
    }


    private boolean isTokenExpired (String token) {
        return this.extractExpiration(token).before(new Date());
    }


    private Date extractExpiration (String token) {
        return this.extractClaims(token, Claims::getExpiration);
    }


    private Claims extractAllClaims (String token) {
        return Jwts
                .parser()
                .verifyWith(this.getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String generateToken (User user) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + this.days * 24 * 60 * 60 * 1000))
                .signWith(getSignInKey())
                .compact();
    }


    private SecretKey getSignInKey () {
        return Keys
                .hmacShaKeyFor(
                     Decoders
                    .BASE64URL
                    .decode(secretKey)
                );
    }

}
