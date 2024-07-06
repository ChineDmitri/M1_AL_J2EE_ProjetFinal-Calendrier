package com.esgi.calendar.service;

import com.esgi.calendar.business.Theme;
import com.esgi.calendar.dto.req.RegistrationFormDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends AuthenticationProvider,
                                      UserDetailsService {

    public void register(RegistrationFormDto form, Theme theme);

    public void performLogout(HttpServletRequest request);
        
}