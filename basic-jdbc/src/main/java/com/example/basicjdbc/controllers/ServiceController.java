package com.example.basicjdbc.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    @GetMapping(path = "/unsecured")
    public String unsecured() {
        return "Unsecured endpoint";
    }

    @GetMapping(path = "/secured")
    public String secured() {
        return "Secured endpoint";
    }

    @GetMapping(path = "/secured/withwrite")
    public String securedWithWrite() {
        return "Secured endpoint with write authority";
    }

}
