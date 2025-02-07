package ru.alex.burdovitsin.mesh.services.impl;

import org.springframework.stereotype.Service;
import ru.alex.burdovitsin.mesh.exception.InvalidOperationException;
import ru.alex.burdovitsin.mesh.exception.UserNotFoundException;
import ru.alex.burdovitsin.mesh.model.jpa.EmailData;
import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.model.rest.*;
import ru.alex.burdovitsin.mesh.repository.EmailDataRepository;
import ru.alex.burdovitsin.mesh.repository.UserRepository;
import ru.alex.burdovitsin.mesh.services.UserService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EmailDataRepository emailDataRepository;

    public UserServiceImpl(UserRepository userRepository, EmailDataRepository emailDataRepository) {
        this.userRepository = userRepository;
        this.emailDataRepository = emailDataRepository;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Long emailOperation(String userName, EmailOperation operation) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));
        switch (operation.getOperation()) {
            case CREATE:
                return addEmailToUser(user, operation.getEmail());
            case UPDATE:
                return updateUserEmail(user, operation.getEmailId(), operation.getEmail());
            case DELETE:
                return deleteEmail(user, operation.getEmailId());
            default:
                throw new InvalidOperationException();
        }
    }

    @Override
    public Long phoneOperation(String userName, PhoneOperation operation) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));
        switch (operation.getOperation()) {
            case CREATE:
                return addPhoneToUser(user, operation.getPhoneNumber());
            case UPDATE:
                return updateUserPhone(user, operation.getPhoneId(), operation.getPhoneNumber());
            case DELETE:
                return deletePhone(user, operation.getPhoneId());
            default:
                throw new InvalidOperationException();
        }
    }

    @Override
    public List<UserItem> getUserList(UserSeekRequest request) {
        return List.of();
    }

    @Override
    public BigDecimal moneyTransfer(String userName, MoneyTransferOperation operation) {
        return null;
    }

    private Long addEmailToUser(User user, String email) {
        long userCountWithEmail = emailDataRepository.countByEmail(email);
        if (userCountWithEmail == 0) {
            EmailData newEmail = new EmailData();
            newEmail.setUserId(user.getId());
            newEmail.setEmail(email);
            user.getEmailData().add(newEmail);
            userRepository.saveAndFlush(user);
        }
        return 0L;
    }

    private Long updateUserEmail(User user, Long id, String email) {
        return 0L;
    }

    private Long deleteEmail(User user, Long id) {
        return 0L;
    }

    private Long addPhoneToUser(User user, String phoneNumber) {
        return 0L;
    }

    private Long updateUserPhone(User user, Long phoneId, String phoneNumber) {
        return 0L;
    }

    private Long deletePhone(User user, Long phoneId) {
        return 0L;
    }
}
