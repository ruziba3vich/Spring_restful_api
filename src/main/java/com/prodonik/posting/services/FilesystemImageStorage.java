package com.prodonik.posting.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FilesystemImageStorage implements ImageStorage {

    private final String uploadPath = "images";

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        Path filePath = Paths.get(uploadPath + File.separator + fileName);
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }

    private String generateUniqueFileName (String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }

    public void deleteImage(String fileName) throws IOException {
        Path filePath = Paths.get(this.uploadPath + File.separator + fileName);
        Files.deleteIfExists(filePath); 
    }

}
