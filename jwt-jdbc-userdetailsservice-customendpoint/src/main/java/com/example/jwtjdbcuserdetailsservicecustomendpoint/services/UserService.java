package com.example.jwtjdbcuserdetailsservicecustomendpoint.services;

import com.example.jwtjdbcuserdetailsservicecustomendpoint.configs.AuthConfig;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.dtos.LoginRequest;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.dtos.LoginResponse;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.entities.User;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthConfig authConfig;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager, AuthConfig authConfig) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.authConfig = authConfig;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null) {
            List<GrantedAuthority> rolesAndAuthorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                rolesAndAuthorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", role.getName().toUpperCase())));
                role.getAuthorities().forEach(authority -> rolesAndAuthorities.add(new SimpleGrantedAuthority(authority.getName().toUpperCase())));
            });
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, rolesAndAuthorities);
        } else {
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }
    }

    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse response = null;
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        if(user != null) {
            UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            try {
                Authentication authentication = authenticationManager.authenticate(credentials);

                if(authentication != null) {
                    List<GrantedAuthority> rolesAndAuthorities = new ArrayList<>();
                    user.getRoles().forEach(role -> {
                        rolesAndAuthorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", role.getName().toUpperCase())));
                        role.getAuthorities().forEach(authority -> rolesAndAuthorities.add(new SimpleGrantedAuthority(authority.getName().toUpperCase())));
                    });
                    String rolesAndAuthoritiesClaim = rolesAndAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(authConfig.getClaimsSeparator()));
                    Claims claims = Jwts.claims();
                    claims.put(authConfig.getClaimRolesAuthorities(), rolesAndAuthoritiesClaim);
                    long currentTimestamp = System.currentTimeMillis();
                    Date expiration = new Date(currentTimestamp + authConfig.getTokenValidity());
                    String token = Jwts.builder()
                            .setClaims(claims)
                            .setSubject(user.getUsername())
                            .setIssuedAt(new Date(currentTimestamp))
                            .setExpiration(expiration)
                            .signWith(SignatureAlgorithm.HS256, authConfig.getTokeyKey())
                            .compact();
                    response = new LoginResponse(authConfig.getTokenType(), token, expiration.toString());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return response;
    }

}
