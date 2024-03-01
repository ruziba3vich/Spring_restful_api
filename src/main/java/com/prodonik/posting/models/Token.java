package com.prodonik.posting.models;

import lombok.Data;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(generator = "UUID")
    UUID id;

    @Column(name = "token")
    private String token;

    @Column(name = "is_logged_out")
    private boolean loggedOut;

    @ManyToOne 
    @JoinColumn(name = "user_id")
    private User user;
}
