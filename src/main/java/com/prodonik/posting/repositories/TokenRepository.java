package com.prodonik.posting.repositories;

import com.prodonik.posting.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    @Query("""
            SELECT t FROM Token t WHERE t.user.id = :userId AND t.loggedOut = false
            """)
    List<Token> findAllTokenByUser(@Param("userId") UUID userId);

    Optional<Token> findByToken(String token);
}
