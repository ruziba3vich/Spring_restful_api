package com.prodonik.posting.controllers;

import com.prodonik.posting.DTOs.PostRequestDTO;
import com.prodonik.posting.models.Post;
import com.prodonik.posting.services.PostingService;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
// @RequestMapping("/api/")
public class PostController {

    private final PostingService postingService;

    public PostController(PostingService postingService) {
        this.postingService = postingService;
    }

    @PostMapping("/post")
    public Post createPost(@RequestBody PostRequestDTO requestDTO) throws IOException {

        if (requestDTO.getImageFile() != null && ! requestDTO.getImageFile().isEmpty()) {
            return this.postingService.createPostWithImage(
                                        requestDTO.getOwner(),
                                        requestDTO.getTitle(),
                                        requestDTO.getContent(),
                                        requestDTO.getImageFile());
        } else {
            return this.postingService.createPost(
                                        requestDTO.getOwner(),
                                        requestDTO.getTitle(),
                                        requestDTO.getContent()
            );
        }
    }

    @PutMapping("/update-post-title/{id}")
    public void updatePostTitle (@PathVariable UUID id, @RequestBody String newTitile) {
        this.postingService.updatePostTitle(id, newTitile);
    }

    @PutMapping("/update-post-content/{id}")
    public void updatePostContent (@PathVariable UUID id, @RequestBody String newContent) {
        this.postingService.updatePostContent(id, newContent);
    }

}
