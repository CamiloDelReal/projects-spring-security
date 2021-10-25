package com.example.jwtjdbcuserdetailsservicecustomendpoint.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponse {
    private String tokenType;
    private String token;
    private String expiration;
}
