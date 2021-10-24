package com.example.basicjdbcuserdetailsserviceauthprovider.services;

import com.example.basicjdbcuserdetailsserviceauthprovider.entities.User;
import com.example.basicjdbcuserdetailsserviceauthprovider.security.UserDetailsImpl;
import com.example.basicjdbcuserdetailsserviceauthprovider.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null) {
            return new UserDetailsImpl(user);
        } else {
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }
    }

}
