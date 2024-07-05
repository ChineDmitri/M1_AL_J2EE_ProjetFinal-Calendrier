package com.esgi.calendar.service;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.GifOfDayDto;

import java.time.LocalDate;
import java.util.List;

public interface ICalendarService {

    public List<DayOfActualMonthDto> getWeeklyCalendar(int week);

    public DayOfActualMonthDto getDayOfActualMonth(int idDay);

    public int addGifForDay(GifOfDayDto dto, UserCustomer user, int idDay);

}
