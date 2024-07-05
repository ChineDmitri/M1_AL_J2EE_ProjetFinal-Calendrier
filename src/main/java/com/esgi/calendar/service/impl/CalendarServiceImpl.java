package com.esgi.calendar.service.impl;

import com.esgi.calendar.business.*;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.GifOfDayDto;
import com.esgi.calendar.mapper.DayOfActualMonthMapper;
import com.esgi.calendar.mapper.GifOfDayMapper;
import com.esgi.calendar.repository.DayOfActualMonthRepository;
import com.esgi.calendar.repository.EmojiRepository;
import com.esgi.calendar.repository.GifOfDayRepository;
import com.esgi.calendar.repository.ReactionRepository;
import com.esgi.calendar.service.ICalendarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CalendarServiceImpl implements ICalendarService {

    private final DayOfActualMonthRepository dayOfActualMonthRepository;
    private final GifOfDayRepository         gifOfDayRepository;
    private final EmojiRepository            emojiRepository;
    private final ReactionRepository         reactionRepository;

    private final DayOfActualMonthMapper dayOfActualMapper;
    private final GifOfDayMapper         gifOfDayMapper;

    @Override
    public List<DayOfActualMonthDto> getWeeklyCalendar(int week) {
        LocalDate today = LocalDate.now();
        LocalDate startDate;

        if (week == 0) {
            startDate = today.withDayOfMonth(1);
        } else {
            startDate = today.withDayOfMonth(1)
                             .plusWeeks(week);
        }
        LocalDate endDate = startDate.plusDays(6);
        return this.dayOfActualMonthRepository.findDaysBetwenTwoDate(startDate, endDate)
                                              .stream()
                                              .map(dayOfActualMapper::toDto)
                                              .toList();

    }

    @Override
    public DayOfActualMonthDto getDayOfActualMonth(int idDay) {
        Optional<DayOfActualMonth> day = this.dayOfActualMonthRepository.findById(Long.valueOf(
                idDay));

        return day.map(dayOfActualMapper::toDto)
                  .orElse(null);
    }

    @Override
    public DayOfActualMonthDto addGifForDay(GifOfDayDto dto,
                                            UserCustomer user,
                                            int idDay) {
        GifOfDay gif = this.gifOfDayMapper.toEntity(dto);
        gif.setUserOwner(user);
        gif = this.gifOfDayRepository.save(gif);

        DayOfActualMonth day = this.dayOfActualMonthRepository.findById(Long.valueOf(idDay))
                                                              .orElse(null);

        if (day != null) {
            day.setGifOfDay(gif);
            this.dayOfActualMonthRepository.save(day);
            return this.dayOfActualMapper.toDto(day);
        }

        return null;
    }

    @Override
    public GifOfDayDto addReactionForDayWithGif(int idDay,
                                                Long idGif,
                                                UserCustomer user) {
        Emoji emoji = this.emojiRepository.findById(idGif)
                                          .orElse(null);

        Reaction reaction = new Reaction();
        reaction.setUserCustomer(user);
        reaction.setEmoji(emoji);
        this.reactionRepository.save(reaction);

        GifOfDay gif = this.gifOfDayRepository.findById(Long.valueOf(idDay))
                                              .orElse(null);

        if (gif != null) {
            reaction.setGifOfDay(gif);
            gif.getReactions().add(reaction);
            this.gifOfDayRepository.save(gif);
            return this.gifOfDayMapper.toDto(gif);
        }

        return null;
    }

}
