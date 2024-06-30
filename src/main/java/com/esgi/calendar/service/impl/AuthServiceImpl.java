package com.esgi.calendar.service.impl;

import com.esgi.calendar.business.Theme;
import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.dto.req.RegistrationForm;
import com.esgi.calendar.repository.UserRepository;
import com.esgi.calendar.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IUserService {

    private UserRepository  userRepository;
    private PasswordEncoder passwordEncoder;

    public void register(RegistrationForm form, Theme theme) {


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
        String password = authentication.getCredentials().toString();
        if (username.trim()
                 .isEmpty()) {
            throw new UsernameNotFoundException(
                    "Le email d'utilisateur ne peut pas être vide");
        }

        UserCustomer utilisateur = userRepository.findByEmailIgnoreCase(username);

        if (utilisateur == null) {
            throw new UsernameNotFoundException("Utilisateur " + username + " introuvable");
        }
        List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(utilisateur);

        return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
    }

    private List<GrantedAuthority> getGrantedAuthorities(UserCustomer utilisateur) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
