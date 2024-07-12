package com.esgi.calendar.controller.rest;

import com.esgi.calendar.config.SecurityConfiguration;
import com.esgi.calendar.controller.AbstractController;
import com.esgi.calendar.controller.ExceptionController;
import com.esgi.calendar.dto.req.FileUploadRequestDto;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.GenericResponseDto;
import com.esgi.calendar.dto.res.GifOfDayDto;
import com.esgi.calendar.exception.TechnicalException;
import com.esgi.calendar.service.ICalendarService;
import com.esgi.calendar.service.IFileService;
import com.esgi.calendar.service.impl.FileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("api")
@Tag(
        name = "Upload GIF",
        description = "API - Televersement de fichier GIF pour un jour de moi en cours"
)
@AllArgsConstructor

public class UploadRestController extends AbstractController {

    private final IFileService     fileService;
    private final ICalendarService calendarService;


    @PostMapping(
            value = "/upload-gif/day/{idDay}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(
            description = "Méthode qui permet d'uploader un GIF à un jour du mois",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Accepted",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = DayOfActualMonthDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Technical error occurred",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = GenericResponseDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = GenericResponseDto.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<?> handleFileUpload(
            @Parameter(description = "Numero de jour du mois en cours")
            @PathVariable
            int idDay,
            @RequestPart("file") MultipartFile file,
            @RequestPart("legende") String legende
    ) {
        FileUploadRequestDto reqDto = new FileUploadRequestDto(file, legende);

        try {
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

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                                 .body(resDto);
        } catch (IOException | TechnicalException e) {
            GenericResponseDto responseDto = new GenericResponseDto(
                    e instanceof IOException ?
                            HttpStatus.INTERNAL_SERVER_ERROR :
                            HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new GenericResponseDto(HttpStatus.INTERNAL_SERVER_ERROR,
                                                          ExceptionController.TRAITEMENT_ERROR)
                             );
    }

}
