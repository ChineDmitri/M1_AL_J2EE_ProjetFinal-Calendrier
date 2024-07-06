package com.esgi.calendar.controller;

import com.esgi.calendar.business.GifOfDay;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.GifOfDayDto;
import com.esgi.calendar.exception.TechnicalException;
import com.esgi.calendar.service.ICalendarService;
import com.esgi.calendar.service.impl.FileServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class UploadController extends AbstractController {

    private final FileServiceImpl  fileService;
    private final ICalendarService calendarService;

    private static final String UPLOAD_GIF = "upload-gif";

    @GetMapping("/upload-gif/day/{idDay}")
    public String uploadGif(@PathVariable int idDay, Model mav) throws
                                                                TechnicalException {
        DayOfActualMonthDto day = calendarService.getDayOfActualMonth(idDay);

        mav.addAttribute("day", day);

        return super.getTheme(UPLOAD_GIF);
    }

    @PostMapping("/upload-gif/day/{idDay}")
    public String handleFileUpload(
            @PathVariable int idDay,
            GifOfDayDto dto,
            Model mav,
            MultipartFile file,
            String legend
    ) throws
      TechnicalException {
        DayOfActualMonthDto day = calendarService.getDayOfActualMonth(idDay);
        String              message;

        if (fileService.isGif(file)) {
            try {
                fileService.saveFile(file);

                dto.setUrl("/" + file.getOriginalFilename());
                dto.setLegende(legend);

                this.calendarService.addGifForDay(
                        dto,
                        super.getUserDetails()
                             .getUserCustomer(),
                        idDay
                );
                message = "Fichier téléversé avec succès!";
            } catch (IOException ex) {
                message = "Une erreur est survenue lors du téléversement du fichier : " + ex.getMessage();
                ex.printStackTrace();
            }
        } else {
            message = "Le fichier n'est pas un GIF valide.";
        }

        mav.addAttribute("day", day);
        mav.addAttribute("message", message);

        return super.getTheme(UPLOAD_GIF);
    }
}
