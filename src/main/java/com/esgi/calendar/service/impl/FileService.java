package com.esgi.calendar.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private static final String FILE_DIR = "src/main/resources/static/";

    public boolean isGif(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equals("image/gif");
    }

    public void saveFile(MultipartFile file) throws IOException {
        // On vérifie si le fichier cible existe avant toute opération d'écriture !
        Path directoryPath = Paths.get(FILE_DIR);
        if (Files.notExists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        byte[] bytes = file.getBytes();
        Path path = Paths.get(FILE_DIR + File.separator + file.getOriginalFilename());
        Files.write(path, bytes);
    }
}
