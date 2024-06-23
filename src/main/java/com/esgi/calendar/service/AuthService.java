package com.esgi.calendar.service;

import com.esgi.calendar.business.UserApplication;
import com.esgi.calendar.dto.req.RegistrationForm;
import com.esgi.calendar.repository.UserRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private UserRepository  userRepository;
    private PasswordEncoder passwordEncoder;

    public void register(RegistrationForm form) {
        UserApplication newUser = new UserApplication();
        newUser.setUsername(form.getEmail());
        newUser.setPassword(passwordEncoder.encode(form.getMotDePasse()));
        userRepository.save(newUser);
    }

}
