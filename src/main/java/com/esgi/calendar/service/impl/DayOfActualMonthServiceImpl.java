package com.esgi.calendar.service.impl;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.mapper.DayOfActualMonthMapper;
import com.esgi.calendar.repository.DayOfActualMonthRepository;
import com.esgi.calendar.service.ICalendarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

@Service
@AllArgsConstructor
public class DayOfActualMonthServiceImpl implements ICalendarService {

    private final DayOfActualMonthRepository dayOfActualMonthRepository;
    private final DayOfActualMonthMapper     mapper;

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
        return this.dayOfActualMonthRepository.findDaysBetwenTwoDate(startDate,
                                                                     endDate)
                                              .stream()
                                              .map(mapper::toDto)
                                              .toList();

    }
    
}
