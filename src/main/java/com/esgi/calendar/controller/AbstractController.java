package com.esgi.calendar.controller;

import com.esgi.calendar.business.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractController {

    protected CustomUserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return (CustomUserDetails) principal;
            }
        }
        return null;
    }

    protected String getTheme(String templateName) {
        if (this.getUserDetails()
                .getTheme()
                .getName()
                .equals("Dark"))
            return templateName + "-dark";
        return templateName;
    }

}
