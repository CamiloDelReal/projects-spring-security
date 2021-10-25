package com.example.jwtjdbcuserdetailsservicecustomendpoint.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class AuthConfig {

    @Value("${security.claims-separator}")
    private String claimsSeparator;
    @Value("${security.claim-roles-authorities}")
    private String claimRolesAuthorities;
    @Value("${security.token-type}")
    private String tokenType;
    @Value("${security.token-key}")
    private String tokeyKey;
    @Value("${security.token-validity}")
    private long tokenValidity;

}
