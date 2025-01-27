package ru.alex.burdovitsin.mesh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.burdovitsin.mesh.model.User;
import ru.alex.burdovitsin.mesh.repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/echo")
    public String echo() {
        return "ECHO";
    }

    @GetMapping("/user")
    public User user() {
        return userRepository.findByName("any");
    }
}
