package ru.alex.burdovitsin.mesh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;
import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.model.rest.*;
import ru.alex.burdovitsin.mesh.services.UserService;
import ru.alex.burdovitsin.mesh.validator.PhoneOperationConstraint;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    private final static String MODULE_NAME = "User module";

    private final static String NO_SUCH_ELEMENT_MESSAGE = "No such element";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/echo") // For testing only
    public String echo() {
        return "ECHO";
    }

    @GetMapping("/user") //TODO remove
    public User user(@NotEmpty @Size(max = 500) @RequestParam String userName) {
        return userService.getByUsername(userName);
    }

    @PutMapping("/email_operation")
    public ResponseEntity<Long> emailOperation(@RequestParam String email) {
        //Long emailId =
        return ResponseEntity.ok(Long.valueOf(1L));
    }

    @PutMapping("/phone_operation")
    public ResponseEntity<Long> phoneOperation(@RequestParam @PhoneOperationConstraint PhoneOperation operation) {
        return ResponseEntity.ok(Long.valueOf(1L));
    }

    @PostMapping("/user_list")
    public ResponseEntity<List<UserItem>> getUserList(@RequestBody UserSeekRequest request) {
        return ResponseEntity.ok(List.of());
    }

    @PutMapping("/money_transfer")
    public ResponseEntity<BigDecimal> moneyTransfer(@RequestBody MoneyTransferOperation operation) {
        return ResponseEntity.ok(BigDecimal.ONE);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(DisabledException e) {
        ErrorResponse response = new ErrorResponse(MODULE_NAME, NO_SUCH_ELEMENT_MESSAGE, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
