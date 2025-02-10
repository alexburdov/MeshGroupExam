package ru.alex.burdovitsin.mesh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alex.burdovitsin.mesh.config.security.JwtTokenService;
import ru.alex.burdovitsin.mesh.model.rest.*;
import ru.alex.burdovitsin.mesh.services.UserService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Validated
@RestController
@Tag(
        name = "API пользователя",
        description = "Контроллер для работы с пользователями"
)
public class UserController {

    private final UserService userService;

    private final JwtTokenService tokenService;

    public UserController(UserService userService, JwtTokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Operation(summary = "ЭХО", description = "Позволяет получить строку ECHO с именем пользователя - для отладки")
    @GetMapping("/echo")    // Некоторый лайфхак для проверки доступности - по идее можно не использовать но помогает FE тоже порой
    public String echo(@RequestHeader HttpHeaders headers) {
        String userName = tokenService.extractUserName(headers);
        return "ECHO " + userName;
    }

    @Operation(summary = "Операции по работе с e-mail", description = "Операции по работе с e-mail")
    @PutMapping("/email_operation")
    public ResponseEntity<Long> emailOperation(@RequestHeader HttpHeaders headers,
                                               @RequestBody @Valid EmailOperation operation
    ) {
        String userName = tokenService.extractUserName(headers);
        Long emailId = userService.emailOperation(userName, operation);
        return ResponseEntity.ok(emailId);
    }

    @Operation(summary = "Операции по работе с телефоном", description = "Операции по работе с телефоном")
    @PutMapping("/phone_operation")
    public ResponseEntity<Long> phoneOperation(@RequestHeader HttpHeaders headers,
                                               @RequestBody @Valid PhoneOperation operation
    ) {
        String userName = tokenService.extractUserName(headers);
        Long phoneId = userService.phoneOperation(userName, operation);
        return ResponseEntity.ok(phoneId);
    }

    @Operation(summary = "Перевод", description = "Перевод другому пользователю")
    @PutMapping("/money_transfer")
    public ResponseEntity<BigDecimal> moneyTransfer(@RequestHeader HttpHeaders headers,
                                                    @RequestBody @Valid MoneyTransferOperation operation
    ) {
        String userName = tokenService.extractUserName(headers);
        BigDecimal newBalance = userService.moneyTransfer(userName, operation);
        return ResponseEntity.ok(newBalance);
    }

    @Operation(summary = "Список пользователей", description = "Получить список пользователей по фильтру")
    @PostMapping("/user_list")
    public ResponseEntity<List<UserItem>> getUserList(@RequestBody UserSeekRequest request) {
        List<UserItem> userList = userService.getUserList(request);
        return ResponseEntity.ok(userList);
    }
}
