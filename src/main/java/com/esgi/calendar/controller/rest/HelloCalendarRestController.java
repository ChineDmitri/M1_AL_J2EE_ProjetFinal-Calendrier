package com.esgi.calendar.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@RestController("/api")
@RestController
@RequestMapping("api")
public class HelloCalendarRestController {
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello Calendar";
    }

    @GetMapping("/hello-protected")
    public String helloProtected() {
        return "Hello Protected";
    }
}
