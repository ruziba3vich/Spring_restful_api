package com.prodonik.posting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.prodonik.posting.models.Post;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    @SuppressWarnings("null")
    Optional<Post> findById(UUID id);

    @Modifying 
    @Query("UPDATE Post p SET p.content = :content WHERE p.id = :id")
    void updateContent(@Param("id") UUID id, @Param("content") String content);

    @Modifying 
    @Query("UPDATE Post p SET p.title = :title WHERE p.id = :id")
    void updateTitle(@Param("id") UUID id, @Param("title") String title);
}
