package com.esgi.calendar.controller;

import com.esgi.calendar.dto.req.RegistrationForm;
import com.esgi.calendar.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private AuthService authService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/signup")
    public String Form() {
        return "registration";
    }

    @PostMapping("/signup")
    public String signup(RegistrationForm form) {
        authService.register(form);
        return "redirect:/login";
    }
}
