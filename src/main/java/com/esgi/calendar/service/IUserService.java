package com.esgi.calendar.service;

import com.esgi.calendar.business.Theme;
import com.esgi.calendar.dto.req.RegistrationFormDto;
import org.springframework.security.authentication.AuthenticationProvider;

public interface IUserService extends AuthenticationProvider {

    public void register(RegistrationFormDto form, Theme theme);

}