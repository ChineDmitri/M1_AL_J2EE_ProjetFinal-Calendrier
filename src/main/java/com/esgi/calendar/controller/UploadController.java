package com.esgi.calendar.controller;

import com.esgi.calendar.service.impl.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@AllArgsConstructor
public class UploadController {

    private final FileService fileService;

    @GetMapping("/upload-gif")
    public ModelAndView uploadGif(){
        ModelAndView mav = new ModelAndView("upload-gif");

        mav.addObject("currentDate", _getCurrentDate());
        mav.addObject("pointNumber", _getCurrentUserPoints());

        return mav;
    }

    @PostMapping("upload-gif")
    public ModelAndView handleFileUpload(MultipartFile file){
        ModelAndView mav = new ModelAndView("upload-gif");
        String message;

        if(fileService.isGif(file)) {
            try {
                fileService.saveFile(file);
                message = "Fichier téléversé avec succès!";
            } catch (IOException ex) {
                message = "Erreur lors du téléversement du fichier.";
                ex.printStackTrace();
            }
        } else {
            message = "Le fichier n'est pas un GIF valide.";
        }

        mav.addObject("currentDate", _getCurrentDate());
        mav.addObject("currentPoints", _getCurrentUserPoints());
        mav.addObject("message", message);

        return mav;
    }

    private String _getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return currentDate.format(formatter);
    }

    private int _getCurrentUserPoints() {
        // TODO : Récupérer le nombre de points de l'utilisateur actuellement conecté
        return 31;
    }
}
