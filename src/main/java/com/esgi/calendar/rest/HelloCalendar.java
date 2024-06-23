package com.esgi.calendar.rest;

import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@RestController("${api.prefix}")
@RequestMapping(path = "${apiPrefix}")
public class HelloCalendar {
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello Calendar";
    }

    @GetMapping("/hello-protected")
    public String helloProtected() {
        return "Hello Protected";
    }
}
