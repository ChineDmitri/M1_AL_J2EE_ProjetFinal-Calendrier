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
    SecurityFilterChain filterChain(HttpSecurity http) throws
                                                       Exception {
        http.csrf(csrf -> csrf.disable())
            .formLogin(login -> login.loginProcessingUrl("/login")
                                     .loginPage("/login")
                                     .defaultSuccessUrl("/api/hello", true)
                                     .permitAll())
            .authorizeRequests(req -> {
                req.requestMatchers("/h2/**",
                                    "/api/hello",
                                    "/**",
                                    "/login",
                                    "/register")
                   .permitAll()
                   .anyRequest()
                   .authenticated();
            })
            .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        //            .requestMatchers("/h2-console/**")


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
