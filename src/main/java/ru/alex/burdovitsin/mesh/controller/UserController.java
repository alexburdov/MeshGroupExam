package ru.alex.burdovitsin.mesh.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alex.burdovitsin.mesh.config.security.JwtTokenService;
import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.model.rest.*;
import ru.alex.burdovitsin.mesh.services.UserService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Validated
public class UserController {

    private final UserService userService;

    private final JwtTokenService tokenService;

    public UserController(UserService userService, JwtTokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/echo") // Некоторый лайфхак для проверки доступности
    public String echo(@RequestHeader HttpHeaders headers) {
        String userName = tokenService.extractUserName(headers);
        return "ECHO " + userName;
    }

    @GetMapping("/user") //TODO remove
    public User user(@RequestHeader HttpHeaders headers) {
        String userName = tokenService.extractUserName(headers);
        return userService.getByUsername(userName);
    }

    //TODO реализовать responce через структуры ??????
    @PutMapping("/email_operation")
    public ResponseEntity<Long> emailOperation(@RequestHeader HttpHeaders headers,
                                               @RequestBody @Valid EmailOperation operation
    ) {
        String userName = tokenService.extractUserName(headers);
        Long emailId = userService.emailOperation(userName, operation);
        return ResponseEntity.ok(emailId);
    }

    @PutMapping("/phone_operation")
    public ResponseEntity<Long> phoneOperation(@RequestHeader HttpHeaders headers,
                                               @RequestBody @Valid PhoneOperation operation
    ) {
        String userName = tokenService.extractUserName(headers);
        Long phoneId = userService.phoneOperation(userName, operation);
        return ResponseEntity.ok(phoneId);
    }

    @PutMapping("/money_transfer")
    public ResponseEntity<BigDecimal> moneyTransfer(@RequestHeader HttpHeaders headers,
                                                    @RequestBody @Valid MoneyTransferOperation operation
    ) {
        String userName = tokenService.extractUserName(headers);
        BigDecimal newBalance = userService.moneyTransfer(userName, operation);
        return ResponseEntity.ok(newBalance);
    }

    @PostMapping("/user_list")
    public ResponseEntity<List<UserItem>> getUserList(@RequestBody UserSeekRequest request) {
        List<UserItem> userList = userService.getUserList(request);
        return ResponseEntity.ok(userList);
    }
}
