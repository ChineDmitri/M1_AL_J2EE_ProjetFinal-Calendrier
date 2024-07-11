package com.esgi.calendar.controller.rest;

import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.dto.res.GenericResponseDto;
import com.esgi.calendar.exception.TechnicalException;
import com.esgi.calendar.service.ICalendarService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeeklyCalendarRestControllerTest {

    @Mock
    private ICalendarService calendarService;

    @InjectMocks
    private WeeklyCalendarRestController weeklyCalendarRestController;

    private GenericResponseDto genericResponseDto;

    @BeforeEach
    public void setUp() {
        genericResponseDto = new GenericResponseDto(HttpStatus.BAD_REQUEST,
                                                    "Technical error occurred");
    }

    @SneakyThrows
    @Test
    public void testGetGifForDay_whenTechnicalExceptionThrown_shouldReturn400StatusAndGenericResponseDto() {
        // Arrange
        int idDay = 1;
        doThrow(TechnicalException.class).when(calendarService)
                                         .getDayOfActualMonth(anyInt());

        // Act
        ResponseEntity<?> responseEntity = weeklyCalendarRestController.getGifForDay(idDay);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @SneakyThrows
    @Test
    public void testGetGifForDay_whenDayOfActualMonthDtoReturned_shouldReturn200StatusAndDayOfActualMonthDto() {
        // Arrange
        int                 idDay               = 1;
        DayOfActualMonthDto dayOfActualMonthDto = new DayOfActualMonthDto();
        when(calendarService.getDayOfActualMonth(anyInt())).thenReturn(dayOfActualMonthDto);

        // Act
        ResponseEntity<?> responseEntity = weeklyCalendarRestController.getGifForDay(idDay);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dayOfActualMonthDto, responseEntity.getBody());
    }
}
