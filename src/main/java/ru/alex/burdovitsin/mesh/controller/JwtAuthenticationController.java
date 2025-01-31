package ru.alex.burdovitsin.mesh.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.burdovitsin.mesh.config.security.JwtTokenService;
import ru.alex.burdovitsin.mesh.config.security.JwtUserDetailsService;
import ru.alex.burdovitsin.mesh.model.rest.JwtRequest;
import ru.alex.burdovitsin.mesh.model.rest.JwtResponse;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController()
@RequestMapping("/auth")
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenService tokenService;

    private final JwtUserDetailsService userDetailsService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager,
                                       JwtTokenService tokenService,
                                       JwtUserDetailsService userDetailsService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) {

        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        );
        authenticationManager.authenticate(authentication);

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = tokenService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
