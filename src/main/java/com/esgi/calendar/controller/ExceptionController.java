package com.esgi.calendar.controller;

import com.esgi.calendar.exception.TechnicalException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(TechnicalException.class)
    public ModelAndView handleTechnicalException(TechnicalException ex) {
        ModelAndView modelAndView = new ModelAndView("400");
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

}
