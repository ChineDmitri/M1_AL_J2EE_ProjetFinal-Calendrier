package com.esgi.calendar.controller;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.service.ICalendarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;

@Controller
@AllArgsConstructor
public class WeeklyCalendarController {

    private ICalendarService calendarService;

    @GetMapping("/weekly-calendar/{week}")
    public String showWeeklyCalendar(@PathVariable int week, Model model) {
        List<DayOfActualMonthDto> days = this.calendarService.getWeeklyCalendar(week);
        model.addAttribute("weekData", days);
        return "weekly-calendar";
    }

}
