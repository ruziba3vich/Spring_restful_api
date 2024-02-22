package com.prodonik.posting.DTOs;

import org.springframework.web.multipart.MultipartFile;
import com.prodonik.posting.models.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostRequestDTO {
    private User owner;
    private String title;
    private String content;
    private MultipartFile imageFile;

    public PostRequestDTO(User owner, String title, String content) {
        this.setOwner(owner);
        this.setTitle(title);
        this.setContent(content);
    }

    public PostRequestDTO(User owner, String title, String content, MultipartFile imageFile) {
        this.setOwner(owner);
        this.setTitle(title);
        this.setContent(content);
        this.setImageFile(imageFile);
    }
}
