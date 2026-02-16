package com.isep.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    
    @Value("${app.file-storage.upload-dir:./uploads}")
    private String uploadDir;
    
    public String uploadFile(MultipartFile file, String subdirectory) throws IOException {
        // Create directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir, subdirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
            ? originalFilename.substring(originalFilename.lastIndexOf("."))
            : "";
        String filename = UUID.randomUUID().toString() + extension;
        
        // Save file
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);
        
        // Return relative path
        return subdirectory + "/" + filename;
    }
    
    public void deleteFile(String filePath) throws IOException {
        Path fullPath = Paths.get(uploadDir, filePath);
        if (Files.exists(fullPath)) {
            Files.delete(fullPath);
        }
    }
    
    public boolean fileExists(String filePath) {
        Path fullPath = Paths.get(uploadDir, filePath);
        return Files.exists(fullPath);
    }
}

