package com.example.huko.mongoDB.controllers;

import com.example.huko.mongoDB.models.AuthenticationRequest;
import com.example.huko.mongoDB.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        String token = String.valueOf(userService.authenticateUser(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }
}
