package com.esgi.calendar.service.impl;

import com.esgi.calendar.business.CustomUserDetails;
import com.esgi.calendar.business.Theme;
import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.controller.ExceptionController;
import com.esgi.calendar.dto.req.RegistrationFormDto;
import com.esgi.calendar.exception.AuthException;
import com.esgi.calendar.exception.TechnicalException;
import com.esgi.calendar.repository.ThemeRepository;
import com.esgi.calendar.repository.UserRepository;
import com.esgi.calendar.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class AuthServiceImpl implements IUserService {

    private static final Logger LOGGER = LogManager.getLogger(AuthServiceImpl.class);

    private UserRepository  userRepository;
    private PasswordEncoder passwordEncoder;
    private ThemeRepository themeRepository;


    public void register(RegistrationFormDto form) throws
                                                   TechnicalException {
        if (!form.getEmail()
                 .matches("^[a-zA-Z0-9._%+-]+@esgi\\.fr$")) {

            LOGGER.atError()
                  .log("Invalid email format : {} must be @esgi.fr",
                       form.getEmail());

            throw new TechnicalException(ExceptionController.EMAIL_STRATEGY_ERROR);

        }
        if (userRepository.findByEmailIgnoreCase(form.getEmail()) != null) {
            throw new TechnicalException("Un utilisateur avec cet email existe déjà");
        }

        Optional<Theme> theme = themeRepository.findById(form.getTheme());
        if (theme.isEmpty()) {
            throw new TechnicalException("Le thème choisi n'existe pas");
        }

        UserCustomer newUser = new UserCustomer();
        newUser.setEmail(form.getEmail());
        newUser.setPassword(passwordEncoder.encode(form.getPassword()));
        newUser.setFirstName(form.getFirstName());
        newUser.setLastName(form.getLastName());
        newUser.setTheme(theme.get());
        userRepository.save(newUser);
    }

    @Override
    public void performLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws
                                                                      AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials()
                                        .toString();

        if (username.trim()
                    .isEmpty()) {
            throw new AuthException(
                    "Le email d'utilisateur ne peut pas être vide");
        }

        CustomUserDetails utilisateurDetails = (CustomUserDetails) loadUserByUsername(
                username
        );

        if (!passwordEncoder.matches(password, utilisateurDetails.getPassword())) {
            throw new AuthException("Mot de passe incorrect");
        }

        if (username.equalsIgnoreCase("admin@esgi.fr")) {
            return new UsernamePasswordAuthenticationToken(
                    utilisateurDetails,
                    password,
                    this.getAdminAutroities());
        }

        return new UsernamePasswordAuthenticationToken(
                utilisateurDetails,
                password,
                utilisateurDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws
                                                           UsernameNotFoundException {
        UserCustomer user = userRepository.findByEmailIgnoreCase(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        CustomUserDetails userDetails = new CustomUserDetails(user,
                                                              user.getTotalPoints());

        return userDetails;
    }

    private Collection<? extends GrantedAuthority> getAdminAutroities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"),
                             new SimpleGrantedAuthority("ROLE_USER"));
    }

}
