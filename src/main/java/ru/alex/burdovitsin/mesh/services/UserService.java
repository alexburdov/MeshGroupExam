package ru.alex.burdovitsin.mesh.services;

import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.model.rest.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getByUsername(String username);

    Long emailOperation(String userName, EmailOperation operation);

    Long phoneOperation(String userName, PhoneOperation operation);

    List<UserItem> getUserList(UserSeekRequest request);

    BigDecimal moneyTransfer(String userName, MoneyTransferOperation operation);
}
