package com.esgi.calendar.controller;

import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.GifOfDayDto;
import com.esgi.calendar.exception.TechnicalException;
import com.esgi.calendar.service.ICalendarService;
import com.esgi.calendar.service.impl.FileServiceImpl;
import jakarta.servlet.ServletContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class UploadController extends AbstractController implements ServletContextAware {

    private final FileServiceImpl       fileService;
    private final ICalendarService      calendarService;
    private       ServletContext        servletContext;

    private static final String UPLOAD_GIF = "upload-gif";

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @GetMapping("/upload-gif/day/{idDay}")
    public String uploadGif(@PathVariable int idDay, Model mav) throws
                                                                TechnicalException {
        DayOfActualMonthDto day = calendarService.getDayOfActualMonth(idDay);

        if (day.getGifOfDay() != null) {
            throw new TechnicalException("Un gif est déjà associé à ce jour");
        }

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
        boolean             success;

        if (fileService.isGif(file)) {
            try {
                String staticDir = servletContext.getRealPath("/static/");
                System.out.println("staticDir = " + staticDir);
                fileService.saveFile(file, staticDir);
                //                String staticDir = servletContext.getRealPath("/static/");
                dto.setUrl("/static/" + file.getOriginalFilename());
                dto.setLegende(legend);

                this.calendarService.addGifForDay(
                        dto,
                        super.getUserDetails()
                             .getUserCustomer(),
                        idDay
                );
                message = "Fichier téléversé avec succès!";
                success = true;
            } catch (IOException ex) {
                success = false;
                message = "Une erreur est survenue lors du téléversement du fichier : " + ex.getMessage();
                ex.printStackTrace();
            }
        } else {
            success = false;
            message = "Le fichier n'est pas un GIF valide.";
        }

        mav.addAttribute("day", day);
        mav.addAttribute("success", success);
        mav.addAttribute("message", message);

        return super.getTheme(UPLOAD_GIF);
    }

}
