package com.selcan.documentflow.congigswager;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String UPLOAD_DIR = "uploads/";

    public String saveFile(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return null;
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            return filePath.toString();

        } catch (IOException e) {
            throw new RuntimeException("Could not store file");
        }
    }
}
