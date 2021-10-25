package com.example.jwtjdbcuserdetailsservicecustomendpoint.controllers.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptionFromController(Exception ex) {
        System.out.println("Exception handled");
        return ResponseEntity.internalServerError().build();
    }

}
