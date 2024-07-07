package com.esgi.calendar.controller;

import com.esgi.calendar.config.SecurityConfiguration;
import com.esgi.calendar.dto.req.FileUploadRequestDto;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.GenericResponseDto;
import com.esgi.calendar.dto.res.GifOfDayDto;
import com.esgi.calendar.exception.TechnicalException;
import com.esgi.calendar.service.ICalendarService;
import com.esgi.calendar.service.IFileService;
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
public class UploadController extends AbstractController /*implements ServletContextAware*/ {

    private final FileServiceImpl       fileService;
    private final ICalendarService      calendarService;
    private final SecurityConfiguration securityConfiguration;

    private static final String UPLOAD_GIF = "upload-gif";
    private static final String DIR_GIF    = "/static/";

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
            Model mav,
            FileUploadRequestDto reqDto
    ) throws
      IOException,
      TechnicalException {

        DayOfActualMonthDto day = calendarService.getDayOfActualMonth(idDay);

        GenericResponseDto resDto = this.fileService.saveFile(
                reqDto,
                idDay,
                super.getServletContext()
                     .getRealPath(
                             IFileService.UPLOAD_DIR_GIF
                     ),
                super.getUserDetails()
                     .getUserCustomer()
        );

        boolean success = resDto.getStatus()
                                .is2xxSuccessful() ? true : false;

        mav.addAttribute("day", day);
        mav.addAttribute("success", success);
        mav.addAttribute("message", resDto.getMessage());

        return super.getTheme(UPLOAD_GIF);
    }

}
