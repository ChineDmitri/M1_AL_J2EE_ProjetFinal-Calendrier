package com.esgi.calendar.controller;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.service.IDayOfActualMonthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@AllArgsConstructor
public class WeeklyCalendarController {

    private IDayOfActualMonthService dayOfActualMonthService;

    @GetMapping("/weekly-calendar")
    public String showWeeklyCalendar(Model model) {
        List<DayOfActualMonth> days = dayOfActualMonthService.getBetweenToday();

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
