package ru.alex.burdovitsin.mesh.services.impl;

import org.springframework.stereotype.Service;
import ru.alex.burdovitsin.mesh.exception.InvalidOperationException;
import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.model.rest.*;
import ru.alex.burdovitsin.mesh.repository.UserRepository;
import ru.alex.burdovitsin.mesh.services.UserService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Long emailOperation(EmailOperation operation) {
        switch (operation.getOperation()) {
            case CREATE:
                return addEmailToUser(operation.getUserId(), operation.getEmail());
            case UPDATE:
                return updateUserEmail(operation.getUserId(), operation.getEmailId(), operation.getEmail());
            case DELETE:
                return deleteEmail(operation.getUserId(), operation.getEmailId());
            default:
                throw new InvalidOperationException();
        }
    }

    @Override
    public Long phoneOperation(PhoneOperation operation) {
        switch (operation.getOperation()) {
            case CREATE:
                return addPhoneToUser(operation.getUserId(), operation.getPhoneNumber());
            case UPDATE:
                return updateUserPhone(operation.getUserId(), operation.getPhoneId(), operation.getPhoneNumber());
            case DELETE:
                return deletePhone(operation.getUserId(), operation.getPhoneId());
            default:
                throw new InvalidOperationException();
        }
    }

    @Override
    public List<UserItem> getUserList(UserSeekRequest request) {
        return List.of();
    }

    @Override
    public BigDecimal moneyTransfer(MoneyTransferOperation operation) {
        return null;
    }

    private Long deleteEmail(Long emailId, Long id) {
        return emailId;
    }

    private Long updateUserEmail(Long emailId, Long id, String email) {
        return 0L;
    }

    private Long addEmailToUser(Long userId, String email) {
        return 0L;
    }

    private Long deletePhone(Long userId, Long phoneId) {
        return 0L;
    }

    private Long updateUserPhone(Long userId, Long phoneId, String phoneNumber) {
        return 0L;
    }

    private Long addPhoneToUser(Long userId, String phoneNumber) {
        return 0L;
    }
}
