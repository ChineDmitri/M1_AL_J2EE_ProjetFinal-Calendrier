package com.esgi.calendar.controller;

import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.GifOfDayDto;
import com.esgi.calendar.service.ICalendarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequestMapping("/weekly-calendar")
@AllArgsConstructor
public class WeeklyCalendarController extends AbstractController {

    private ICalendarService calendarService;

    private static final String WEEKLY_CALENDAR = "weekly-calendar";
    private static final String ADD_GIF         = "add-gif-for-day";

    @GetMapping("/{week}")
    public String showWeeklyCalendar(@PathVariable int week, Model model) {
        List<DayOfActualMonthDto> days = this.calendarService.getWeeklyCalendar(week);
        model.addAttribute("weekData", days);
        model.addAttribute("userInfo", super.getUserDetails());

        return super.setTheme(WEEKLY_CALENDAR);
    }

    @GetMapping("/add-gif/day/{idDay}")
    public String addGifForDay(@PathVariable int idDay, Model model) {
        DayOfActualMonthDto day = this.calendarService.getDayOfActualMonth(idDay);
        model.addAttribute("day", day);

        return super.setTheme(ADD_GIF);
    }

    @PostMapping("/add-gif/day/{idDay}")
    public String addGifForDay2(@PathVariable int idDay, GifOfDayDto dto, Model model) {
        int numberWeek = this.calendarService.addGifForDay(
                dto,
                super.getUserDetails()
                     .getUserCustomer(),
                idDay
        );

        return String.format("redirect:/%s/%s", WEEKLY_CALENDAR, numberWeek);
    }

}
