package com.esgi.calendar.service;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.GifOfDayDto;
import com.esgi.calendar.exception.TechnicalException;

import java.time.LocalDate;
import java.util.List;

public interface ICalendarService {

    public List<DayOfActualMonthDto> getWeeklyCalendar(int week);

    public DayOfActualMonthDto getDayOfActualMonth(int idDay) throws
                                                              TechnicalException;

    public DayOfActualMonthDto addGifForDay(GifOfDayDto dto, UserCustomer user,
                                            int idDay) throws
                                                       TechnicalException;

    public GifOfDayDto addReactionForDayWithGif(int idDay,
                                                Long idGif,
                                                UserCustomer user) throws
                                                                   TechnicalException;
}
