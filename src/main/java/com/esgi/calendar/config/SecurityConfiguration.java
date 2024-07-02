package com.esgi.calendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }


    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {

        return new CustomAuthenticationSuccessHandler();

    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws
                                                       Exception {

        http.csrf(csrf -> csrf.disable())
            .authorizeRequests(authorizeRequests -> authorizeRequests
                    .requestMatchers("/h2/**", "/api/hello", "/login", "/signup")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )
            .formLogin(formLogin -> formLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/weekly-calendar", true)
                    .successHandler(customAuthenticationSuccessHandler())
                    .permitAll()
            )
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();

    }


}