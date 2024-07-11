package com.esgi.calendar.controller.rest;

import com.esgi.calendar.business.Theme;
import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.init.InitialDataSetup;
import com.esgi.calendar.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.net.http.HttpHeaders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@WithMockUser(username = "testuser", roles = "USER")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)/*C'est pour @BeforeAll soit pas static*/
//@DataJpaTest
public class WeeklyCalendarRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    //    @Autowired
    //    InitialDataSetup dataSetup;

    //    @Autowired
    //    private TestRestTemplate testRestTemplate;

    //    @Autowired
    //    private UserRepository userRepository;


    @BeforeAll
    public static void setUp() throws
                               Exception {
//        this.dataSetup.init();
    }

    @Test
    void testGetGifForDaySuccess() throws
                                   Exception {
        int validDayId = 1;

        mockMvc.perform(get("/day/" + validDayId).secure(true))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(validDayId));
    }

    @Test
    void testGetGifForDayTechnicalError() throws
                                          Exception {
        int invalidDayId = -1;

        mockMvc.perform(get("/day/" + invalidDayId))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

}
