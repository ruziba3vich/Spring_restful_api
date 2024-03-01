package com.prodonik.posting.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prodonik.posting.models.User;

@Repository
public interface UserRepository extends JpaRepository <User, UUID> {
    Optional<User> findByUsername (String username);
    @SuppressWarnings("null")
    List<User> findAll();
    @SuppressWarnings("null")
    Optional<User> findById(UUID id);
}
