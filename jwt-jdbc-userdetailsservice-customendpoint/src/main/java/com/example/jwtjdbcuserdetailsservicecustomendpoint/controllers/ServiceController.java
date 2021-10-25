package com.example.jwtjdbcuserdetailsservicecustomendpoint.controllers;

import com.example.jwtjdbcuserdetailsservicecustomendpoint.dtos.LoginRequest;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.dtos.LoginResponse;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
public class ServiceController {

    private final UserService userService;

    @Autowired
    public ServiceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/unsecured")
    public String unsecured() {
        return "Unsecured endpoint";
    }

    @GetMapping(path = "/secured")
    public String secured() {
        return "Secured endpoint";
    }

    @GetMapping(path = "/secured/withadmin")
    public String securedWithAdmin() {
        return "Secured endpoint with admin role";
    }

    @GetMapping(path = "/secured/withcreate")
    public String securedWithWrite() {
        return "Secured endpoint with create user authority";
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest);
        if(response != null) {
            return ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
