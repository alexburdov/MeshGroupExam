package ru.alex.burdovitsin.mesh.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.repository.UserRepository;
import ru.alex.burdovitsin.mesh.services.UserService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
