package com.esgi.calendar.service.impl;

import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.dto.res.GenericResponseDto;
import com.esgi.calendar.dto.res.GifOfDayDto;
import com.esgi.calendar.exception.TechnicalException;
import com.esgi.calendar.service.ICalendarService;
import com.esgi.calendar.service.IFileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
@Log4j2
public class FileServiceImpl implements IFileService {

    private static final Logger           LOGGER = LogManager.getLogger(FileServiceImpl.class);
    private final        ICalendarService calendarService;

    /**
     * Cette fonction vérifie si le fichier donné en paramètre est un fichier GIF ou non.
     *
     * @param file - Le fichier à vérifier
     * @return Booléen - True si le fichier n'est pas null ET que c'est un GIF, False si le fichier est null ou différent d'un GIF.
     */
    public boolean isGif(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equals("image/gif");
    }

    /**
     * Cette fonction a pour but d'enregistrer un fichier GIF sur le disque dur du serveur (répertoire resources/static)
     *
     * @param file
     * @throws IOException
     */
    public GenericResponseDto saveFile(MultipartFile file,
                                       String legend,
                                       String serverPath,
                                       int idDay,
                                       UserCustomer user) throws
                                                          IOException,
                                                          TechnicalException {
        GenericResponseDto response;

        if (this.isGif(file)) {
//            try {
                // On vérifie si le fichier cible existe avant toute opération d'écriture !
                Path directoryPath = Paths.get(serverPath);
                if (Files.notExists(directoryPath)) {
                    LOGGER.atInfo()
                          .log("Le répertoire {} n'existe pas, création du répertoire : {}",
                               directoryPath,
                               serverPath);
                    Files.createDirectories(directoryPath);
                }
                // On sauvegarde le fichier sur le disque dur du serveur
                byte[] bytes = file.getBytes();
                Path   path  = Paths.get(serverPath + file.getOriginalFilename());
                Files.write(path, bytes);

                GifOfDayDto dto = new GifOfDayDto();
                dto.setUrl(IFileService.UPLOAD_DIR_GIF + file.getOriginalFilename());
                dto.setLegende(legend);

                this.calendarService.addGifForDay(
                        dto,
                        user,
                        idDay
                );

                response = GenericResponseDto.builder()
                                             .status(HttpStatus.OK)
                                             .message("Fichier téléversé avec succès!")
                                             .build();
//            } catch (IOException ex) {
//                response = GenericResponseDto.builder()
//                                             .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                             .message("Fichier téléversé avec succès!")
//                                             .build();
//            }
        } else {
            response = GenericResponseDto.builder()
                                         .status(HttpStatus.BAD_REQUEST)
                                         .message("Le fichier n'est pas un GIF valide.")
                                         .build();
            //            success = false;
            //            message = "Le fichier n'est pas un GIF valide.";
        }


        return response;
    }
}
