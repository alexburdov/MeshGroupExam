package ru.alex.burdovitsin.mesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.burdovitsin.mesh.model.jpa.EmailData;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {
    long countByEmail(String email);
}
