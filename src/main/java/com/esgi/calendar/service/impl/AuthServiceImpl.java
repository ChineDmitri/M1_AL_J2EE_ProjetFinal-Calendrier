package com.esgi.calendar.service.impl;

import com.esgi.calendar.business.CustomUserDetails;
import com.esgi.calendar.business.Theme;
import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.dto.req.RegistrationFormDto;
import com.esgi.calendar.repository.UserRepository;
import com.esgi.calendar.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IUserService {

    private UserRepository  userRepository;
    private PasswordEncoder passwordEncoder;

    public void register(RegistrationFormDto form, Theme theme) {


        UserCustomer newUser = new UserCustomer();
        newUser.setEmail(form.getEmail());
        newUser.setPassword(passwordEncoder.encode(form.getPassword()));
        newUser.setFirstName(form.getFirstName());
        newUser.setLastName(form.getLastName());
        newUser.setTheme(theme);
        userRepository.save(newUser);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws
                                                                      AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials()
                                        .toString();
        if (username.trim()
                    .isEmpty()) {
            throw new UsernameNotFoundException(
                    "Le email d'utilisateurDetails ne peut pas être vide");
        }

        CustomUserDetails utilisateurDetails = (CustomUserDetails) loadUserByUsername(
                username);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                utilisateurDetails,
                password,
                utilisateurDetails.getAuthorities());

        return token;
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

}
