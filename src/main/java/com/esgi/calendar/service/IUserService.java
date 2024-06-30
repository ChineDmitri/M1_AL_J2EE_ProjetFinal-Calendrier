package com.esgi.calendar.service;

import com.esgi.calendar.business.Theme;
import com.esgi.calendar.dto.req.RegistrationForm;
import org.springframework.security.authentication.AuthenticationProvider;

public interface IUserService extends AuthenticationProvider {

    public void register(RegistrationForm form, Theme theme);

}