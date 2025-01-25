package ru.alex.burdovitsin.mesh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/echo")
    public String echo() {
        return "ECHO";
    }
}
