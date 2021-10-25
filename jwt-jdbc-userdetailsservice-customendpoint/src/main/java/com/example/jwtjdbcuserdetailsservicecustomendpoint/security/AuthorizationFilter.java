package com.example.jwtjdbcuserdetailsservicecustomendpoint.security;

import com.example.jwtjdbcuserdetailsservicecustomendpoint.configs.AuthConfig;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.entities.User;
import com.example.jwtjdbcuserdetailsservicecustomendpoint.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthorizationFilter extends OncePerRequestFilter {

    private final AuthConfig authConfig;
    private final UserService userService;

    public AuthorizationFilter(AuthConfig authConfig, UserService userService) {
        this.authConfig = authConfig;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerAuthorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(headerAuthorization != null && headerAuthorization.startsWith(authConfig.getTokenType())) {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            if(authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = null;
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).replace(authConfig.getTokenType(), "");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(authConfig.getTokeyKey())
                    .parseClaimsJws(token)
                    .getBody();
            String username = claims.getSubject();
            User user = userService.findByUsername(username);
            if(user != null) {
                List<GrantedAuthority> rolesAndAuthorities = Stream.of(((String)claims.get(authConfig.getClaimRolesAuthorities())).split(authConfig.getClaimsSeparator()))
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                authentication = new UsernamePasswordAuthenticationToken(username, null, rolesAndAuthorities);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return authentication;
    }

    // We can avoid overriding this method and use preFilters over the controller's methods
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().equals("/login") || request.getRequestURI().equals("/unsecured");
    }
}
