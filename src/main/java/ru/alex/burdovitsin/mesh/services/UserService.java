package ru.alex.burdovitsin.mesh.services;

import ru.alex.burdovitsin.mesh.model.jpa.User;

import java.util.Optional;

public interface UserService {

    User getByUsername(String username);

}
