package com.esgi.calendar.controller.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WeeklyCalendarRestControllerIntegrationTI {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void testGetGifForDaySuccess() throws
                                   Exception {
        int validDayId = 1;

        mockMvc.perform(get("/api/day/" + validDayId).secure(true))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(validDayId));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void testGetGifForDayTechnicalError() throws
                                          Exception {
        int invalidDayId = -1;

        mockMvc.perform(get("/api/day/" + invalidDayId))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    @Test
    void testGetGifForDay_shouldRedirectOnLoginPage() throws Exception {
        mockMvc.perform(get("/api/day/0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"))
                //.andExpect(jsonPath("$.status").value(""))
        ;
    }

}
