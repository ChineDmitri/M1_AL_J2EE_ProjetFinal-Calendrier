package com.esgi.calendar.controller;

import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.EmojiDto;
import com.esgi.calendar.dto.res.GifOfDayDto;
import com.esgi.calendar.service.ICalendarService;
import com.esgi.calendar.service.IEmojiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping("/weekly-calendar")
@AllArgsConstructor
public class WeeklyCalendarController extends AbstractController {

    private ICalendarService calendarService;
    private IEmojiService    emojiService;

    private static final String WEEKLY_CALENDAR = "weekly-calendar";
    private static final String ADD_GIF         = "add-gif-for-day";
    private static final String ADD_EMOJI       = "add-emoji-for-day";

    @GetMapping("/{week}")
    public String showWeeklyCalendar(@PathVariable int week, Model model) {
        List<DayOfActualMonthDto> days = this.calendarService.getWeeklyCalendar(week);
        model.addAttribute("weekData", days);
        model.addAttribute("userInfo", super.getUserDetails());

        return super.getTheme(WEEKLY_CALENDAR);
    }

    @GetMapping("/add-gif/day/{idDay}")
    public String addGifForDay(@PathVariable int idDay, Model model) {
        DayOfActualMonthDto day = this.calendarService.getDayOfActualMonth(idDay);
        model.addAttribute("day", day);

        return super.getTheme(ADD_GIF);
    }

    @PostMapping("/add-gif/day/{idDay}")
    public String addGifForDay2(@PathVariable int idDay, GifOfDayDto dto, Model model) {
        DayOfActualMonthDto dayOfActualMonth = this.calendarService.addGifForDay(
                dto,
                super.getUserDetails()
                     .getUserCustomer(),
                idDay
        );

        return String.format("redirect:/%s/%s",
                             WEEKLY_CALENDAR,
                             this.getNumberWeek(idDay));
    }

    @GetMapping("/add-emoji/day/{idDay}")
    public String getPageForAddEmoji(@PathVariable int idDay, Model model) {
        DayOfActualMonthDto day    = this.calendarService.getDayOfActualMonth(idDay);
        List<EmojiDto>      emojis = this.emojiService.getAllEmoji();
        model.addAttribute("day", day);
        model.addAttribute("emojis", emojis);

        return super.getTheme(ADD_EMOJI);
    }

    @PostMapping("/add-emoji/day/{idDay}")
    public String addEmojiForDay(@PathVariable int idDay,
                                 @RequestParam("reaction") Long emojiId,
                                 Model model) {
        GifOfDayDto day = this.calendarService.addReactionForDayWithGif(
                idDay,
                emojiId,
                super.getUserDetails()
                     .getUserCustomer()
        );

        return String.format("redirect:/%s/%s",
                             WEEKLY_CALENDAR,
                             this.getNumberWeek(idDay));
    }


    private int getNumberWeek(int idDay) {
        return (idDay - 1) == 0 ? 0 : (idDay - 1) / 7;
    }


}
