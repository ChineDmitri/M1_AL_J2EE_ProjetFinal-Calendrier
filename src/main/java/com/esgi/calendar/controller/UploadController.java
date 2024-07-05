package com.esgi.calendar.controller;

import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.service.ICalendarService;
import com.esgi.calendar.service.impl.FileServiceImpl;
import com.esgi.calendar.service.impl.UserCustomerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final ICalendarService calendarService;

    private static final String WEEKLY_CALENDAR = "weekly-calendar";

    @GetMapping("/upload-gif/day/{idDay}")
    public ModelAndView uploadGif(@PathVariable int idDay){
        ModelAndView mav = new ModelAndView("upload-gif");

        DayOfActualMonthDto day = calendarService.getDayOfActualMonth(idDay);

        mav.addObject("day", day);

        return mav;
    }

    @PostMapping("/upload-gif/day/{idDay}")
    public ModelAndView handleFileUpload(@PathVariable int idDay, MultipartFile file, String legend){
        ModelAndView mav = new ModelAndView("upload-gif");
        DayOfActualMonthDto day = calendarService.getDayOfActualMonth(idDay);
        String message;

        if(fileService.isGif(file)) {
            try {
                fileService.saveFile(file, legend, _getCurrentUser());
                message = "Fichier téléversé avec succès!";
            } catch (IOException ex) {
                message = "Une erreur est survenue lors du téléversement du fichier : " + ex.getMessage();
                ex.printStackTrace();
            }
        } else {
            message = "Le fichier n'est pas un GIF valide.";
        }

        mav.addObject("day", day);
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
}
