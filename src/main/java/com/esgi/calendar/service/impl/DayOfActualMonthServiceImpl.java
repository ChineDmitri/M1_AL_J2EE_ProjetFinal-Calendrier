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

    //    public List<DayOfActualMonth> getAfter7Days() {
    //        LocalDate currentDate = LocalDate.now();
    //        LocalDate startDate = currentDate.minusDays(
    //                currentDate
    //                        .getDayOfWeek()
    //                        .getValue() - 1
    //        );
    //        LocalDate endDate = currentDate.plusDays(6);
    //        return this.dayOfActualMonthRepository.findDaysBetwenTwoDate(currentDate,
    //                                                                     endDate);
    //    }
    //
    //    @Override
    //    public List<DayOfActualMonth> getWeekDatesWithOffset(int offset) {
    //        LocalDate currentDate = LocalDate.now().plusWeeks(offset);
    //        LocalDate startDate = currentDate.minusDays(currentDate.getDayOfWeek().getValue() - 1);
    //        LocalDate endDate = startDate.plusDays(6);
    //        return this.dayOfActualMonthRepository.findDaysBetwenTwoDate(startDate, endDate);
    //    }
    //
    //    public int getCurrentWeekOfMonth(int week, LocalDate today) {
    //        int currentWeekOfMonth = today.get(ChronoField.ALIGNED_WEEK_OF_MONTH);
    //
    //        LocalDate startDateOfWeek;
    //        if (week == currentWeekOfMonth) {
    //            startDateOfWeek = today.minusDays(today.getDayOfWeek()
    //                                                   .getValue() - 1);
    //        } else {
    //            startDateOfWeek = today.withDayOfMonth(1)
    //                                   .plusWeeks(week - 1);
    //            startDateOfWeek = startDateOfWeek.minusDays(startDateOfWeek.getDayOfWeek()
    //                                                                       .getValue() - 1);
    //        }
    //        return currentWeekOfMonth;
    //    }
}
