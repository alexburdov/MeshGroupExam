package ru.alex.burdovitsin.mesh.repository;

import org.springframework.data.repository.CrudRepository;
import ru.alex.burdovitsin.mesh.model.jpa.EmailData;

public interface EmailDataRepository extends CrudRepository<EmailData, Long> {
    long countByEmail(String email);
}
