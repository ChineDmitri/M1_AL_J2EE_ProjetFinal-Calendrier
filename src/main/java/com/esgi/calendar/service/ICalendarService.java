package com.esgi.calendar.service;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;

import java.time.LocalDate;
import java.util.List;

public interface ICalendarService {

    //    public List<DayOfActualMonth> getAfter7Days();
    //
    //    public List<DayOfActualMonth> getWeekDatesWithOffset(int offset);
    //
    //    public int getCurrentWeekOfMonth(int week, LocalDate today);
    public List<DayOfActualMonthDto> getWeeklyCalendar(int week);

}
