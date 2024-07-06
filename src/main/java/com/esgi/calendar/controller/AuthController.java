package com.esgi.calendar.controller;

import com.esgi.calendar.business.Theme;
import com.esgi.calendar.dto.req.RegistrationFormDto;
import com.esgi.calendar.exception.TechnicalException;
import com.esgi.calendar.repository.ThemeRepository;
import com.esgi.calendar.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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

    @GetMapping("login-error")
    public ModelAndView loginError() {
        ModelAndView modelAndView = new ModelAndView("generic-page-error");

        modelAndView.setStatus(HttpStatus.UNAUTHORIZED);
        modelAndView.addObject("code",
                               HttpStatus.UNAUTHORIZED.value());
        modelAndView.addObject("raison",
                               HttpStatus.UNAUTHORIZED.getReasonPhrase());
        modelAndView.addObject("errorMessage",
                               "Veuillez vérifier vos informations de connexion.");
        return modelAndView;
    }


    @GetMapping("/signup")
    public String Form(Model model) {
        List<Theme> themes = themeRepository.findAll();
        model.addAttribute("themes", themes);
        return "registration";
    }

    @PostMapping("/signup")
    public String signup(@Valid RegistrationFormDto form) throws
                                                   TechnicalException {
        authService.register(form);
        return "redirect:/login";
    }

    private String authHandler() {
        if (super.getUserDetails() == null) {
            return "login";
        }
        return "redirect:/weekly-calendar/0";
    }
}
