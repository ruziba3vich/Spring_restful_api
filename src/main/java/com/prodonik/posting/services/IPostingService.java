package com.prodonik.posting.services;

import com.prodonik.posting.models.Post;
import com.prodonik.posting.models.User;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface IPostingService {
    Post createPost (User owner, String title, String content) throws IOException;
    Post createPostWithImage (User owner, String title, String content, MultipartFile imageFile) throws IOException;
    void updatePostContent (UUID postId, String newContent);
    void updatePostTitle (UUID postId, String newTitle);
}
