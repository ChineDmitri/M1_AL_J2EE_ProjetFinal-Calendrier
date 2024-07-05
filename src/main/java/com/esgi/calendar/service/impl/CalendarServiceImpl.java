package com.esgi.calendar.service.impl;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.business.GifOfDay;
import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.GifOfDayDto;
import com.esgi.calendar.mapper.DayOfActualMonthMapper;
import com.esgi.calendar.mapper.GifOfDayMapper;
import com.esgi.calendar.repository.DayOfActualMonthRepository;
import com.esgi.calendar.repository.GifOfDayRepository;
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
    public int addGifForDay(GifOfDayDto dto,
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
            return (idDay - 1) == 0 ? 0 : (idDay - 1) / 7;
        }

        return 0;
    }

}
