package com.esgi.calendar.service.impl;

import com.esgi.calendar.business.GifOfDay;
import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.repository.GifOfDayRepository;
import com.esgi.calendar.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements IFileService {
    private static final String FILE_DIR = "src/main/resources/static/";

    /**
     * Cette fonction vérifie si le fichier donné en paramètre est un fichier GIF ou non.
     * @param file - Le fichier à vérifier
     * @return Booléen - True si le fichier n'est pas null ET que c'est un GIF, False si le fichier est null ou différent d'un GIF.
     */
    public boolean isGif(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equals("image/gif");
    }

    /**
     * Cette fonction a pour but d'enregistrer un fichier GIF sur le disque dur du serveur (répertoire resources/static)
     * @param file
     * @throws IOException
     */
    public void saveFile(MultipartFile file) throws IOException {

        // On vérifie si le fichier cible existe avant toute opération d'écriture !
        Path directoryPath = Paths.get(FILE_DIR);
        if (Files.notExists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // On sauvegarde le fichier sur le disque dur du serveur
        byte[] bytes = file.getBytes();
        Path path = Paths.get(FILE_DIR + File.separator + file.getOriginalFilename());
        Files.write(path, bytes);
    }
}
