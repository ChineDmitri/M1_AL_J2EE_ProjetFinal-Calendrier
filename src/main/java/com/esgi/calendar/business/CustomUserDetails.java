package com.esgi.calendar.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    private UserCustomer userCustomer;
    private int          totalPoints;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return userCustomer.getPassword();
    }

    @Override
    public String getUsername() {
        return userCustomer.getEmail();
    }

    public Theme getTheme() {
        return userCustomer.getTheme();
    }

    public Long getUserId() {
        return userCustomer.getId();
    }


}
