package com.prodonik.posting.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageService {

    private final ImageStorage imageStorage;

    public ImageService(ImageStorage imageStorage) {
        this.imageStorage = imageStorage;
    }

    public String saveImage (MultipartFile file) throws IOException {
        return imageStorage.saveImage(file); 
    }

}
