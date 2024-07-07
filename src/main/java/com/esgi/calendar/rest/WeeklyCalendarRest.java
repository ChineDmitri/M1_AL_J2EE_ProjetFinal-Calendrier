package com.esgi.calendar.rest;

import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.service.ICalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@Tag(name = "Weekly Calendar", description = "Weekly Calendar API")
@AllArgsConstructor
public class WeeklyCalendarRest {

    ICalendarService calendarService;

    @GetMapping("/weekly-calendar/{week}")
    @Operation(description = "Méthode qui permet recuperer les jours de mois en cours")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<DayOfActualMonthDto>> showWeeklyCalendar(
            @Parameter(description = "Numero de la semaine du mois en cours")
            @PathVariable
            int week
    ) {
        return ResponseEntity.ok(this.calendarService.getWeeklyCalendar(week));
    }
}
