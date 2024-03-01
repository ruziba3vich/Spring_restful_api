package com.prodonik.posting.models;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User users;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(name = "image_path")
    private String pathToImage;

    public Post (User users, String title, String content) throws IOException {
        this.setUsers(users);
        this.setTitle(title);
        this.setContent(content);
        this.createdAt = LocalDateTime.now();
    }
}
