package com.esgi.calendar.controller;

import com.esgi.calendar.business.Theme;
import com.esgi.calendar.dto.req.RegistrationForm;
import com.esgi.calendar.repository.ThemeRepository;
import com.esgi.calendar.service.impl.AuthServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AuthController {

    private ThemeRepository themeRepository;
    private AuthServiceImpl authService;


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/signup")
    public String Form(Model model) {
        List<Theme> themes = themeRepository.findAll();
        model.addAttribute("themes", themes);
        return "registration";
    }

    @PostMapping("/signup")
    public String signup(RegistrationForm form) {
        Optional<Theme> theme = themeRepository.findById(form.getTheme());
        if (theme.isPresent()) {
            authService.register(form, theme.get());
            return "redirect:/login";
        }
        return "redirect:/error";
    }
}
