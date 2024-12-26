package com.gateway.user_service.controller;

import com.gateway.user_service.model.UserResponseModel;
import com.gateway.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private Environment environment;

    private UserService userService;

    @GetMapping("/status")
    public String status(){
        System.out.println(String.format(environment.getProperty("albums.url"), "123"));
        return "Running on port: " + environment.getProperty("local.server.port")
                + ", value=" + environment.getProperty("label.value")
                + ", user.home=" + environment.getProperty("user.home")
                + ", label.hello=" + environment.getProperty("label.hello");

    }

    @PostMapping
    public String createUser(){
        return "User created";
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId){
        return ResponseEntity.ofNullable(userService.findByUserId(userId));
    }
}
