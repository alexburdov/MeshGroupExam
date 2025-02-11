package ru.alex.burdovitsin.mesh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.burdovitsin.mesh.model.rest.JwtRequest;
import ru.alex.burdovitsin.mesh.model.rest.JwtResponse;
import ru.alex.burdovitsin.mesh.services.AuthenticationService;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController()
@RequestMapping("/auth")
@Tag(
        name = "Jwt Аутентификация",
        description = "Контроллер для аутентификации JWT"
)
public class JwtAuthenticationController {

    private final AuthenticationService authenticationService;

    public JwtAuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Получение token пользователя", description = "Позволяет получить Token для зарегистрированого пользователя")
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@Parameter(description = "Индетификатор пользователя", required = true)
                                                       @Valid @RequestBody JwtRequest request
    ) {
        final String token
                = authenticationService.createAuthenticationToken(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
