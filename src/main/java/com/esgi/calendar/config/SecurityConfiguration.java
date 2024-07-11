package com.esgi.calendar.config;

import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.ServletContextAware;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration implements ServletContextAware {


    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext = servletContext;
    }

    public String getServerStaicDirPath() {
        return this.servletContext.getRealPath("/static/");
    }

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
                    .requestMatchers("/api/hello", "/login", "/signup")
                    .permitAll()
                    .requestMatchers("/swagger-ui/**", "/h2/**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated()
            )
            .formLogin(formLogin -> formLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/weekly-calendar/0", true)
                    // .successHandler(customAuthenticationSuccessHandler())
                    .failureUrl("/login-error")
                    .permitAll()
            )
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();

    }

}