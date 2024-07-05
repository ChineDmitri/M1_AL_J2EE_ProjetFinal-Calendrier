package com.esgi.calendar.mapper;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = GifOfDayMapper.class)
public interface DayOfActualMonthMapper {

    DayOfActualMonthDto toDto(DayOfActualMonth dayOfActualMonth);

    DayOfActualMonth toEntity(DayOfActualMonthDto dayOfActualMonthDto);
}
