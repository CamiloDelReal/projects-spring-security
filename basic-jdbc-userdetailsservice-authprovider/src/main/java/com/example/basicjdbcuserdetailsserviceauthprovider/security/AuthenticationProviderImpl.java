package com.example.basicjdbcuserdetailsserviceauthprovider.security;

import com.example.basicjdbcuserdetailsserviceauthprovider.entities.User;
import com.example.basicjdbcuserdetailsserviceauthprovider.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationProviderImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        if(user != null) {
            if(passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(String.format("ROLE_%s", user.getRole().toUpperCase()))));
            } else {
                throw new BadCredentialsException("Invalid password");
            }
        }
        throw new BadCredentialsException(String.format("Username %s not found", authentication.getName()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
