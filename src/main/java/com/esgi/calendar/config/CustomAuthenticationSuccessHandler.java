package com.esgi.calendar.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws
                                                                       IOException,
                                                                       ServletException {

        if (authentication != null && authentication.isAuthenticated()) {
            response.sendRedirect("/weekly-calendar/0");
        } else {
            response.sendRedirect("/login");
        }

    }


}
