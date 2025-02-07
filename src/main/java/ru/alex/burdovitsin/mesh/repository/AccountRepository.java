package ru.alex.burdovitsin.mesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.burdovitsin.mesh.model.jpa.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
