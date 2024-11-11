package com.gateway.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private Environment environment;

    @GetMapping("/status")
    public String status(){
        return "Running on port: " + environment.getProperty("local.server.port")
                + ", value=" + environment.getProperty("label.value");
    }

    @PostMapping
    public String createUser(){
        return "User created";
    }
}
