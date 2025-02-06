package ru.alex.burdovitsin.mesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.burdovitsin.mesh.model.jpa.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);


}
