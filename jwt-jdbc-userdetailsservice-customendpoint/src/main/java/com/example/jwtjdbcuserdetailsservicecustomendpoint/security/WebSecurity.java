package com.example.jwtjdbcuserdetailsservicecustomendpoint.security;

import com.example.jwtjdbcuserdetailsservicecustomendpoint.configs.AuthConfig;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final AuthConfig authConfig;
    private final UserService userService;

    @Autowired
    public WebSecurity(AuthConfig authConfig, @Lazy  UserService userService) {
        this.authConfig = authConfig;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/unsecured", "/login").permitAll()    // AuthrozationFilter will skip these
                .antMatchers("/secured").authenticated()
                .antMatchers("/secured/withadmin").hasRole("ADMIN")
                .antMatchers("/secured/withcreate").hasAuthority("CREATE_USER")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(providesAuthorizationFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(providesPasswordEncoder());
        super.configure(auth);
    }

    @Bean
    public AuthenticationManager providesAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public PasswordEncoder providesPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AuthorizationFilter providesAuthorizationFilter() {
        return new AuthorizationFilter(authConfig, userService);
    }

}
