package com.prodonik.posting.filters;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.prodonik.posting.services.JwtService;
import com.prodonik.posting.services.UserService;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain)
            throws ServletException, IOException {
                String authHeader = request.getHeader("Authorization");
                if (authHeader == null || ! authHeader.startsWith("Bearer ")) {
                    filterChain.doFilter(request, response);
                }
                else {
                    String token = authHeader.substring(7);
                    String username = jwtService.extractUsername(token);
                    if (username != null && SecurityContextHolder
                                            .getContext()
                                            .getAuthentication() == null) {

                        UserDetails userDetails = userService.loadUserByUsername(username);
                        if (jwtService.isValid(token, userDetails)) {
                            UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                            authToken.setDetails(
                                new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                            );
                            
                            SecurityContextHolder
                            .getContext()
                            .setAuthentication(authToken);
                        }
                    }

                    filterChain.doFilter(request, response);
                }
    }
    
}
