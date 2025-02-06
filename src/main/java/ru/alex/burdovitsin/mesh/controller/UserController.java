package ru.alex.burdovitsin.mesh.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.model.rest.*;
import ru.alex.burdovitsin.mesh.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Validated
public class UserController {

    private final static String MODULE_NAME = "User module";

    private final static String NO_SUCH_ELEMENT_MESSAGE = "No such element";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/echo") // Некоторый лайфхак для проверки доступности
    public String echo() {
        return "ECHO";
    }

    @GetMapping("/user") //TODO remove
    public User user(@NotEmpty @Size(max = 500) @RequestParam String userName) {
        return userService.getByUsername(userName);
    }

    //TODO реализовать responce через структуры ??????
    @PutMapping("/email_operation")
    public ResponseEntity<Long> emailOperation(@RequestBody @Valid EmailOperation operation) {
        Long emailId = userService.emailOperation(operation);
        return ResponseEntity.ok(emailId);
    }

    @PutMapping("/phone_operation")
    public ResponseEntity<Long> phoneOperation(@RequestBody @Valid PhoneOperation operation) {
        Long phoneId = userService.phoneOperation(operation);
        return ResponseEntity.ok(phoneId);
    }

    @PostMapping("/user_list")
    public ResponseEntity<List<UserItem>> getUserList(@RequestBody UserSeekRequest request) {
        List<UserItem> userList = userService.getUserList(request);
        return ResponseEntity.ok(userList);
    }

    @PutMapping("/money_transfer")
    public ResponseEntity<BigDecimal> moneyTransfer(@RequestBody MoneyTransferOperation operation) {
        BigDecimal newBalance = userService.moneyTransfer(operation);
        return ResponseEntity.ok(newBalance);
    }
}
