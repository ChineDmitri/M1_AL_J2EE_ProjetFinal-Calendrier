package com.esgi.calendar.controller.rest;

import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.GenericResponseDto;
import com.esgi.calendar.exception.TechnicalException;
import com.esgi.calendar.service.ICalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@Tag(name = "Weekly Calendar", description = "Weekly Calendar API")
@AllArgsConstructor
public class WeeklyCalendarRestController {

    ICalendarService calendarService;

    @GetMapping("/weekly-calendar/{week}")
    @Operation(
            description = "Méthode qui permet recuperer les jours de mois en cours",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Day data retrieved successfully",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = DayOfActualMonthDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "When empty list"
                    )
            }
    )
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<DayOfActualMonthDto>> showWeeklyCalendar(
            @Parameter(description = "Numero de la semaine du mois en cours")
            @PathVariable
            int week
    ) {
        List<DayOfActualMonthDto> res = this.calendarService.getWeeklyCalendar(week);
        if (res.isEmpty()) {
            return ResponseEntity.noContent()
                                 .build();
        }
        return ResponseEntity.ok(this.calendarService.getWeeklyCalendar(week));
    }


    @GetMapping("/day/{idDay}")
    @Operation(
            description = "Méthode qui permet de récupérer un jour du mois",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Day data retrieved successfully",
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
                    )
            }
    )
    public ResponseEntity<?> getGifForDay(
            @Parameter(description = "Numero de jour du mois en cours")
            @PathVariable
            int idDay
    ) {
        try {
            return ResponseEntity.ok(this.calendarService.getDayOfActualMonth(idDay));
        } catch (TechnicalException e) {
            GenericResponseDto res = new GenericResponseDto(HttpStatus.BAD_REQUEST,
                                                            e.getMessage());
            return ResponseEntity.status(res.getStatus())
                                 .body(res);
        }
    }
}
