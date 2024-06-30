package com.esgi.calendar.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class LoginRequestMatcher implements RequestMatcher {


    @Override
    public boolean matches(HttpServletRequest request) {
        return request.getRequestURI()
                      .equals("/login");
    }


}
