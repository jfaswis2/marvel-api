package com.jfas.marvelapi.service;

import com.jfas.marvelapi.dto.security.LoginRequest;
import com.jfas.marvelapi.dto.security.LoginResponse;
import com.jfas.marvelapi.persistence.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final HttpSecurity http;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(HttpSecurity http, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.http = http;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }


    public LoginResponse authenticate(LoginRequest loginRequest) {
        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.username());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, loginRequest.password(), user.getAuthorities()
        );
        authenticationManager.authenticate(authentication);
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        return new LoginResponse(jwt);
    }

    private Map<String, Object> generateExtraClaims(UserDetails user) {
        Map<String, Object> extraClaims = new HashMap<>();

        String roleName = ((User) user).getRole().getName().name();
        extraClaims.put("role", roleName);
        extraClaims.put("authorities", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return extraClaims;
    }


    public void logout(){
        try{
            http.logout(logoutConfig -> logoutConfig.deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
