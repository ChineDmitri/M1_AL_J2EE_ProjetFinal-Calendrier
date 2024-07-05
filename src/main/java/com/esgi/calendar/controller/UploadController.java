package com.esgi.calendar.controller;

import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.service.impl.FileServiceImpl;
import com.esgi.calendar.service.impl.UserCustomerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final FileServiceImpl fileService;
    private final UserCustomerServiceImpl userCustomerService;

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
        String currentDate = _getCurrentDate();

        if(fileService.isGif(file)) {

            try {
                fileService.saveFile(file, _getCurrentUser());
                message = "Fichier téléversé avec succès!";
            } catch (IOException ex) {
                message = "Erreur lors du téléversement du fichier.";
                ex.printStackTrace();
            }
        } else {
            message = "Le fichier n'est pas un GIF valide.";
        }

        mav.addObject("currentDate", currentDate);
        mav.addObject("currentPoints", _getCurrentUserPoints());
        mav.addObject("message", message);

        return mav;
    }

    private UserCustomer _getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if(principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return userCustomerService.recupererUserCustomerParMail(email);
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
