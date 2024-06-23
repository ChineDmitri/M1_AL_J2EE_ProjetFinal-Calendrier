package com.esgi.calendar.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("${rest.prefix}")
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "error OMG :(";
    }

    public String getErrorPath() {
        return "/error";
    }
}
