package com.esgi.calendar.controller;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.EmojiDto;
import com.esgi.calendar.dto.res.GifOfDayDto;
import com.esgi.calendar.exception.TechnicalException;
import com.esgi.calendar.repository.DayOfActualMonthRepository;
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

    private ICalendarService           calendarService;
    private IEmojiService              emojiService;
    private DayOfActualMonthRepository dayOfActualMonthRepository;


    @GetMapping("/{week}")
    public String showWeeklyCalendar(@PathVariable int week, Model model) {
        List<DayOfActualMonthDto> days = this.calendarService.getWeeklyCalendar(week);

        long TotalDay = this.dayOfActualMonthRepository.countAllDays()
                                                       .intValue();

        int lastWeek = (int) Math.ceil(TotalDay / 7);
        model.addAttribute("lastWeek", lastWeek);

        model.addAttribute("weekData", days);
        model.addAttribute("userInfo", super.getUserDetails());

        return super.getTheme(WEEKLY_CALENDAR);
    }

    @GetMapping("/add-gif/day/{idDay}")
    public String getPageToAddGifForDay(@PathVariable int idDay, Model model) throws
                                                                              TechnicalException {
        DayOfActualMonthDto day = this.calendarService.getDayOfActualMonth(idDay);

        if (day.getGifOfDay() != null) {
            throw new TechnicalException("Un gif est déjà associé à ce jour");
        }

        model.addAttribute("day", day);

        return super.getTheme(ADD_GIF);
    }

    @PostMapping("/add-gif/day/{idDay}")
    public String addGifForDay(@PathVariable int idDay, GifOfDayDto dto,
                               Model model) throws
                                            TechnicalException {
        DayOfActualMonthDto dayOfActualMonth = this.calendarService.addGifForDay(
                dto,
                super.getUserDetails()
                     .getUserCustomer(),
                idDay
        );

        return String.format("redirect:/%s/%s",
                             WEEKLY_CALENDAR,
                             super.getNumberWeek(idDay));
    }

    @GetMapping("/add-emoji/day/{idDay}")
    public String getPageForAddEmoji(@PathVariable int idDay, Model model) throws
                                                                           TechnicalException {
        DayOfActualMonthDto day = this.calendarService.getDayOfActualMonth(idDay);

        if (day.getGifOfDay() == null) {
            throw new TechnicalException("Il n'y a pas de gif pour ce jour!");
        }

        List<EmojiDto> emojis = this.emojiService.getAllEmoji();
        model.addAttribute("day", day);
        model.addAttribute("emojis", emojis);

        return super.getTheme(ADD_EMOJI);
    }

    @PostMapping("/add-emoji/day/{idDay}")
    public String addEmojiForDay(@PathVariable int idDay,
                                 @RequestParam("reaction") Long emojiId,
                                 Model model) throws
                                              TechnicalException {
        DayOfActualMonth dayEntity = this.dayOfActualMonthRepository.findById(Long.valueOf(
                                                 idDay))
                                                                    .orElse(null);

        GifOfDayDto day = this.calendarService.addReactionForDayWithGif(
                idDay,
                emojiId,
                super.getUserDetails()
                     .getUserCustomer()
        );

        return String.format("redirect:/%s/%s",
                             WEEKLY_CALENDAR,
                             super.getNumberWeek(idDay));
    }


    //    private int getNumberWeek(int idDay) {
    //        return (idDay - 1) == 0 ? 0 : (idDay - 1) / 7;
    //    }


}
