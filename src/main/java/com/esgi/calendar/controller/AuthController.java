package com.esgi.calendar.controller;

import com.esgi.calendar.business.Theme;
import com.esgi.calendar.dto.req.RegistrationFormDto;
import com.esgi.calendar.repository.ThemeRepository;
import com.esgi.calendar.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AuthController extends AbstractController {

    private ThemeRepository themeRepository;
    private AuthServiceImpl authService;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        this.authService.performLogout(request);
        return "redirect:/"; // Redirect to the login page or any desired logout destination
    }

    @GetMapping("/")
    public String mainPage() {
        return this.authHandler();
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return this.authHandler();
    }


    @GetMapping("/signup")
    public String Form(Model model) {
        List<Theme> themes = themeRepository.findAll();
        model.addAttribute("themes", themes);
        return "registration";
    }

    @PostMapping("/signup")
    public String signup(RegistrationFormDto form) {
        Optional<Theme> theme = themeRepository.findById(form.getTheme());
        if (theme.isPresent()) {
            authService.register(form, theme.get());
            return "redirect:/login";
        }
        return "redirect:/error";
    }

    private String authHandler() {
        if (super.getUserDetails() == null) {
            return "login";
        }
        return "redirect:/weekly-calendar/0";
    }
}
