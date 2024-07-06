package com.esgi.calendar.service;

import com.esgi.calendar.business.Theme;
import com.esgi.calendar.dto.req.RegistrationFormDto;
import com.esgi.calendar.exception.TechnicalException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends AuthenticationProvider,
                                      UserDetailsService {

    public void register(RegistrationFormDto form) throws
                                                   TechnicalException;

    public void performLogout(HttpServletRequest request);

}