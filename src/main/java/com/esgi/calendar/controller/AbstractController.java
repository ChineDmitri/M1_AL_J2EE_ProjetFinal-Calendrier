package com.esgi.calendar.controller;

import com.esgi.calendar.business.CustomUserDetails;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.ServletContextAware;

public abstract class AbstractController implements ServletContextAware {

    protected static final String WEEKLY_CALENDAR = "weekly-calendar";
    protected static final String ADD_GIF         = "add-gif-for-day";
    protected static final String ADD_EMOJI       = "add-emoji-for-day";

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

//    protected String getGifDirectory() {
//        return this.uploadDirGif;
//    }

    protected ServletContext getServletContext() {
        return this.servletContext;
    }

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

    public int getNumberWeek(int idDay) {
        return (idDay - 1) == 0 ? 0 : (idDay - 1) / 7;
    }

}
