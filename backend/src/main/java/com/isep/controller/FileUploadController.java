package com.isep.controller;

import com.isep.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class FileUploadController {
    
    private final FileUploadService fileUploadService;
    
    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false, defaultValue = "images") String directory) {
        try {
            String filePath = fileUploadService.uploadFile(file, directory);
            Map<String, String> response = new HashMap<>();
            response.put("url", "/uploads/" + filePath);
            response.put("path", filePath);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/document")
    public ResponseEntity<Map<String, String>> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false, defaultValue = "documents") String directory) {
        try {
            String filePath = fileUploadService.uploadFile(file, directory);
            Map<String, String> response = new HashMap<>();
            response.put("url", "/uploads/" + filePath);
            response.put("path", filePath);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}

