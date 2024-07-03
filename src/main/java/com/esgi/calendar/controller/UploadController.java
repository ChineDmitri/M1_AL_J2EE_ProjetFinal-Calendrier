package com.esgi.calendar.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@AllArgsConstructor
public class UploadController {
    @GetMapping("/upload-gif")
    public ModelAndView uploadGif(){
        ModelAndView mav = new ModelAndView("upload-gif");

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);

        // TODO : Récupérer le nombre de points de l'utilisateur actuellement conecté
        int nombrePoints = 31;

        mav.addObject("currentDate", formattedDate);
        mav.addObject("pointNumber", nombrePoints);

        return mav;
    }
}
