package com.esgi.calendar.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DayOfActualMonthDto {

    private Long id;

    private LocalDate date;

    private GifOfDayDto gifOfDay;

    private int costGif;

}
