package ru.alex.burdovitsin.mesh.repository;

import org.springframework.data.repository.CrudRepository;
import ru.alex.burdovitsin.mesh.model.jpa.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
