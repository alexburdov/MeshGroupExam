package ru.alex.burdovitsin.mesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.burdovitsin.mesh.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
