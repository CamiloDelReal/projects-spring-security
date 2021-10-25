package com.example.jwtjdbcuserdetailsservicecustomendpoint.seeders;

import com.example.jwtjdbcuserdetailsservicecustomendpoint.entities.Authority;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.entities.Role;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.entities.User;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.repositories.AuthorityRepository;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.repositories.RoleRepository;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseSeeder {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseSeeder(AuthorityRepository authorityRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void seed(ContextRefreshedEvent contextRefreshedEvent) {
        if(authorityRepository.count() == 0 && roleRepository.count() == 0 && userRepository.count() == 0) {
            // Seeding authorities
            Authority createUserAuthority = new Authority("CREATE_USER");
            createUserAuthority = authorityRepository.save(createUserAuthority);
            Authority readUserAuthority = new Authority("READ_USER");
            readUserAuthority = authorityRepository.save(readUserAuthority);
            Authority updateUserAuthority = new Authority("UPDATE_USER");
            updateUserAuthority = authorityRepository.save(updateUserAuthority);
            Authority deleteUserAuthority = new Authority("DELETE_USER");
            deleteUserAuthority = authorityRepository.save(deleteUserAuthority);

            // Seeding roles
            Role guestRole = new Role("GUEST", List.of(readUserAuthority));
            guestRole = roleRepository.save(guestRole);
            Role adminRole = new Role("ADMIN", List.of(createUserAuthority, readUserAuthority, updateUserAuthority, deleteUserAuthority));
            adminRole = roleRepository.save(adminRole);

            // Seeding users
            User guestUser = new User("guest", passwordEncoder.encode("123"), List.of(guestRole));
            userRepository.save(guestUser);
            User adminUser = new User("admin", passwordEncoder.encode("123"), List.of(adminRole));
            userRepository.save(adminUser);
        }
    }

}
