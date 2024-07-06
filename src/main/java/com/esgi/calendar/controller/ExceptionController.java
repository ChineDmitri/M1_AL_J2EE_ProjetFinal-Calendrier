package com.esgi.calendar.controller;

import com.esgi.calendar.exception.AuthException;
import com.esgi.calendar.exception.TechnicalException;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class ExceptionController {


    public static final String TRAITEMENT_ERROR =
            "Erreur lors du traitement. Veuillez vérifier les informations saisies ou contacter le support technique.";


    public static final String EMAIL_STRATEGY_ERROR =
            "Erreur lors de la création du compte. L'email doit être au format @esgi.fr.";


    @ExceptionHandler(TechnicalException.class)
    public ModelAndView handleTechnicalException(TechnicalException ex) {
        ModelAndView modelAndView = new ModelAndView("generic-page-error");
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.addObject("code", HttpStatus.BAD_REQUEST.value());
        modelAndView.addObject("raison", HttpStatus.BAD_REQUEST.getReasonPhrase());
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(IOException.class)
    public ModelAndView handleServerException(IOException ex) {
        ModelAndView modelAndView = this.prepareModelAndView(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("code", HttpStatus.UNAUTHORIZED.value());
        modelAndView.addObject("raison", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        return modelAndView;
    }

    @ExceptionHandler(AuthException.class)
    public ModelAndView handleAuthException(AuthException ex) {
        ModelAndView modelAndView = this.prepareModelAndView(ex, HttpStatus.UNAUTHORIZED);

        return modelAndView;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolationException(
            DataIntegrityViolationException ex
    ) {
        ModelAndView modelAndView = this.prepareModelAndView(ex, HttpStatus.BAD_REQUEST);

        String errorMessage;
        if (ex.getMessage()
              .contains("email")) {
            errorMessage = "Erreur lors de la création du compte. L'email est déjà utilisé.";
        } else {
            errorMessage = ExceptionController.TRAITEMENT_ERROR;
        }

        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView handleConstraintViolationException(
            ConstraintViolationException ex) {
        ModelAndView modelAndView = this.prepareModelAndView(ex, HttpStatus.BAD_REQUEST);

        String errorMessage;
        if (ex.getMessage()
              .contains("email")) {
            errorMessage = ExceptionController.EMAIL_STRATEGY_ERROR;
        } else {
            errorMessage = ExceptionController.TRAITEMENT_ERROR;
        }

        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

    private ModelAndView prepareModelAndView(Exception ex, HttpStatus status) {
        ModelAndView modelAndView = new ModelAndView("generic-page-error");
        modelAndView.setStatus(status);
        modelAndView.addObject("code", status.value());
        modelAndView.addObject("raison", status.getReasonPhrase());
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

}
