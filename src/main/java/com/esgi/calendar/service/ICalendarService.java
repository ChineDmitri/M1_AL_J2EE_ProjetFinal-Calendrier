package com.esgi.calendar.service;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;

import java.time.LocalDate;
import java.util.List;

public interface ICalendarService {
    
    public List<DayOfActualMonthDto> getWeeklyCalendar(int week);

}
