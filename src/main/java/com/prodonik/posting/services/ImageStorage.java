package com.prodonik.posting.services;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ImageStorage {
    String saveImage(MultipartFile file) throws IOException;
}
