package com.esgi.calendar.controller.rest;

import com.esgi.calendar.dto.res.DayOfActualMonthDto;
import com.esgi.calendar.exception.TechnicalException;
import com.esgi.calendar.service.ICalendarService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class WeeklyCalendarRestIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICalendarService calendarService;

    @Test
    @WithMockUser(username = "testuser", roles = {"ROLE_USER"})
    void showWeeklyCalendar_shouldReturnDays_whenDaysExist() throws Exception {

        DayOfActualMonthDto day1 = new DayOfActualMonthDto(
                1L,
                LocalDate.of(2024, 7, 1),
                null,
                10
        );

        DayOfActualMonthDto day2 = new DayOfActualMonthDto(
                2L,
                LocalDate.of(2024, 7, 2),
                null,
                15
        );

        List<DayOfActualMonthDto> days = Arrays.asList(day1, day2);

        Mockito.when(calendarService.getWeeklyCalendar(1)).thenReturn(days);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/weekly-calendar/1")
                .accept(MediaType.APPLICATION_JSON)
                .with(request -> {
                    request.setRemoteUser("toto");
                    return request;
                });

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dayNumber").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value("2024-07-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gifUrl").value("GIF1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dayNumber").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].date").value("2024-07-02"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].gifUrl").value("GIF2"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void showWeeklyCalendar_shouldReturnNoContent_whenNoDaysExist() throws Exception {
        Mockito.when(calendarService.getWeeklyCalendar(1)).thenReturn(Collections.emptyList());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/weekly-calendar/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getGifForDay_shouldReturnDay_whenDayExists() throws Exception {
        DayOfActualMonthDto day = new DayOfActualMonthDto(
                1L,
                LocalDate.of(2024, 7, 1),
                null,
                10
        );
        Mockito.when(calendarService.getDayOfActualMonth(1)).thenReturn(day);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/day/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dayNumber").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value("2024-07-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gifUrl").value("GIF1"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getGifForDay_shouldReturnBadRequest_whenTechnicalExceptionOccurs() throws Exception {
        Mockito.when(calendarService.getDayOfActualMonth(1)).thenThrow(new TechnicalException("Technical error occurred"));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/day/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Technical error occurred"))
                .andDo(MockMvcResultHandlers.print());
    }
}
