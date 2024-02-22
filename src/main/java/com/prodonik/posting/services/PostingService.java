package com.prodonik.posting.services;

import com.prodonik.posting.models.Post;
import com.prodonik.posting.models.User;
import com.prodonik.posting.repositories.PostRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class PostingService implements IPostingService {

    private final PostRepository postRepository;
    private final ImageService imageService;

    public PostingService(PostRepository postRepository, ImageService imageService) {
        this.postRepository = postRepository;
        this.imageService = imageService;
    }

    @Override
    public Post createPost(User owner, String title, String content) throws IOException {
        Post post = new Post(owner, title, content);
        return postRepository.save(post);
    }

    @Override
    public Post createPostWithImage (User owner, String title, String content, MultipartFile imageFile) throws IOException {
        String image_path = imageService.saveImage(imageFile);
        Post post = new Post(owner, title, content);
        post.setPathToImage(image_path);
        return postRepository.save(post);
    }

    @Transactional
    @Override 
    public void updatePostContent(UUID postId, String newContent) {
        postRepository.updateContent(postId, newContent); 
    }

    @Transactional
    @Override 
    public void updatePostTitle(UUID postId, String newTitle) {
        postRepository.updateTitle(postId, newTitle);
    }

}
