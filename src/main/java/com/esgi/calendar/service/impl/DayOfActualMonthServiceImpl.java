package com.esgi.calendar.service.impl;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.repository.DayOfActualMonthRepository;
import com.esgi.calendar.service.IDayOfActualMonthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class DayOfActualMonthServiceImpl implements IDayOfActualMonthService {

    private final DayOfActualMonthRepository dayOfActualMonthRepository;

    public List<DayOfActualMonth> getBetweenToday() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(
                currentDate
                        .getDayOfWeek()
                        .getValue() - 1
        );
        LocalDate endDate = currentDate.plusDays(6);
        return this.dayOfActualMonthRepository.findDaysBeetwenTwoDate(currentDate,
                                                                      endDate);
    }
}
