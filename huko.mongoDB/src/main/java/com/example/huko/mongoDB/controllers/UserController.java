package com.example.huko.mongoDB.controllers;

import com.example.huko.mongoDB.CustomizedResponse;
import com.example.huko.mongoDB.models.AuthenticationRequest;
import com.example.huko.mongoDB.models.UserModel;
import com.example.huko.mongoDB.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.logging.Logger;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        var response = new CustomizedResponse("A list of all Users", userService.getUsers());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getAUsers(@PathVariable("id") String id) {
        var response = new CustomizedResponse("User with id " + id, Collections.singletonList(userService.getAUser(id)));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = "/users", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity createUser(@RequestBody UserModel user) {
        var response = new CustomizedResponse("User Created Successfully", Collections.singletonList(userService.addUser(user)));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = "/authenticate", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity authenticateUser(@RequestBody AuthenticationRequest authRequest) {
        logger.info("Received authentication request for email: " + authRequest.getEmail());
        String token = userService.authenticateUser(authRequest.getEmail(), authRequest.getPassword());
        String message = token != null ? "Authentication Successful" : "Authentication Failed";
        HttpStatus status = token != null ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        var response = new CustomizedResponse(message, token);
        return new ResponseEntity(response, status);
    }
}
