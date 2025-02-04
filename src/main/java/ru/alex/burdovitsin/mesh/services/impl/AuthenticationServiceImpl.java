package ru.alex.burdovitsin.mesh.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.alex.burdovitsin.mesh.config.security.JwtTokenService;
import ru.alex.burdovitsin.mesh.config.security.JwtUserDetailsService;
import ru.alex.burdovitsin.mesh.services.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService tokenService;

    private final JwtUserDetailsService userDetailsService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     JwtTokenService tokenService,
                                     JwtUserDetailsService userDetailsService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public String createAuthenticationToken(String username, String password) {
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return tokenService.generateToken(userDetails);
    }
}
