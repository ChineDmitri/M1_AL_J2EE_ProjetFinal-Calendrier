package com.esgi.calendar.controller;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.service.IDayOfActualMonthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@AllArgsConstructor
public class WeeklyCalendarController {

    private IDayOfActualMonthService dayOfActualMonthService;

    @GetMapping("/weekly-calendar")
    public String showWeeklyCalendar(Model model) {
        List<DayOfActualMonth> days = dayOfActualMonthService.getSevenDaysFromToday();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        model.addAttribute("weekDates",
                           days.stream()
                               .map(day -> day.getDate()
                                              .format(formatter)
                               )
                               .toList()
        );

        return "weekly-calendar";
    }

}
