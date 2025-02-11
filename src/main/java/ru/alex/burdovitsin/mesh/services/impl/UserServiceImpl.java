package ru.alex.burdovitsin.mesh.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.burdovitsin.mesh.common.CommonUtils;
import ru.alex.burdovitsin.mesh.exception.InvalidOperationException;
import ru.alex.burdovitsin.mesh.exception.LowFundsException;
import ru.alex.burdovitsin.mesh.exception.UserNotFoundException;
import ru.alex.burdovitsin.mesh.mappers.UserMapper;
import ru.alex.burdovitsin.mesh.model.jpa.Account;
import ru.alex.burdovitsin.mesh.model.jpa.EmailData;
import ru.alex.burdovitsin.mesh.model.jpa.PhoneData;
import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.model.rest.*;
import ru.alex.burdovitsin.mesh.repository.EmailDataRepository;
import ru.alex.burdovitsin.mesh.repository.PhoneDataRepository;
import ru.alex.burdovitsin.mesh.repository.UserRepository;
import ru.alex.burdovitsin.mesh.services.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final BigDecimal MAX_MULTIPLIER = BigDecimal.valueOf(2.07);

    private final BigDecimal INCREASED_MULTIPLIER = BigDecimal.valueOf(0.1);

    private final UserRepository userRepository;

    private final EmailDataRepository emailDataRepository;

    private final PhoneDataRepository phoneDataRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           EmailDataRepository emailDataRepository,
                           PhoneDataRepository phoneDataRepository,
                           UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.emailDataRepository = emailDataRepository;
        this.phoneDataRepository = phoneDataRepository;
        this.userMapper = userMapper;
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Long emailOperation(String userName, EmailOperation operation) {
        log.debug("emailOperation {} = {}", userName, operation);
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));
        switch (operation.getOperation()) {
            case CREATE:
                return addEmailToUser(user, operation.getEmail());
            case UPDATE:
                return updateUserEmail(user, operation.getEmailId(), operation.getEmail());
            case DELETE:
                return deleteEmail(user, operation.getEmailId());
            default:
                throw new InvalidOperationException("Invalid e-mail operation");
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Long phoneOperation(String userName, PhoneOperation operation) {
        log.debug("phoneOperation {} = {}", userName, operation);
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));
        switch (operation.getOperation()) {
            case CREATE:
                return addPhoneToUser(user, operation.getPhoneNumber());
            case UPDATE:
                return updateUserPhone(user, operation.getPhoneId(), operation.getPhoneNumber());
            case DELETE:
                return deletePhone(user, operation.getPhoneId());
            default:
                throw new InvalidOperationException("Invalid phone operation");
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<UserItem> getUserList(UserSeekRequest request) {
        log.debug("getUserList {}", request);
        Pageable pageable = CommonUtils.transformToPageable(request);
        request = CommonUtils.normalizeSeekRequest(request);
        List<User> users = userRepository.getUserWithParameters(
                request.getNameStartFrom(),
                request.getDateOfBirthFrom(),
                request.getEmailFull(),
                request.getPhoneNumberFull(),
                pageable);
        return userMapper.usersToUserItems(users);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = {RuntimeException.class})
    public BigDecimal moneyTransfer(String userName, MoneyTransferOperation operation) {
        log.info("moneyTransfer {} = {}", userName, operation);
        User userFrom = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));
        BigDecimal newBalanceUserFrom = userFrom.getAccount().getBalance().subtract(operation.getTransferAmount());
        if (newBalanceUserFrom.compareTo(BigDecimal.ZERO) < 0) {
            throw new LowFundsException();
        }
        userFrom.getAccount().setBalance(newBalanceUserFrom);
        User userTo = userRepository.findById(operation.getTransferToUser()).orElseThrow(() -> new UserNotFoundException(userName));
        BigDecimal newBalanceUserTo = userTo.getAccount().getBalance().add(operation.getTransferAmount());
        userTo.getAccount().setBalance(newBalanceUserTo);
        userRepository.save(userFrom);
        userRepository.save(userTo);
        return newBalanceUserFrom;
    }

    @Override
    public List<User> getUserForIncreaseBalance() {
        return userRepository.getUsersForIncreaseBalance();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = {RuntimeException.class})
    public void increaseAccountBalanceIfNeeded(User user) {
        Account account = user.getAccount();
        if (Objects.nonNull(account)) {
            BigDecimal balance = account.getBalance();
            BigDecimal initialBalance = account.getInitBalance();
            BigDecimal maxIncreasingBalance = initialBalance.multiply(MAX_MULTIPLIER);
            if (balance.compareTo(maxIncreasingBalance) < 0) {
                BigDecimal newBalance = balance.multiply(INCREASED_MULTIPLIER);
                if (newBalance.compareTo(maxIncreasingBalance) > 0) {
                    newBalance = maxIncreasingBalance;
                }
                log.info("Increasing account balance to {} for user {}", newBalance, user);
                account.setBalance(newBalance);
                userRepository.save(user);
            }
        } else {
            log.error("Account for user {} is null", user);
        }
    }

    private Long addEmailToUser(User user, String email) {
        long userCountWithEmail = emailDataRepository.countByEmail(email);
        if (userCountWithEmail == 0) {
            EmailData newEmail = new EmailData();
            newEmail.setUserId(user.getId());
            newEmail.setEmail(email);
            user.getEmailData().add(newEmail);
            log.debug("Add email {} to {}", email, newEmail);
            return userRepository.save(user)
                    .getEmailData()
                    .stream()
                    .filter(e -> e.getEmail().equals(email))
                    .findFirst()
                    .orElseThrow(() -> new InvalidOperationException("E-mail not added"))
                    .getId();
        }
        throw new InvalidOperationException("E-mail exist");
    }

    private Long updateUserEmail(User user, Long emailId, String email) {
        long userCountWithEmail = emailDataRepository.countByEmail(email);
        if (userCountWithEmail == 0) {
            EmailData emailData
                    = user.getEmailData()
                    .stream()
                    .filter(e -> Objects.equals(e.getId(), emailId))
                    .findFirst().orElseThrow(() -> new InvalidOperationException("E-mail not updated"));
            emailData.setEmail(email);
            userRepository.save(user);
            log.debug("email {} updated", email);
            return emailId;
        }
        throw new InvalidOperationException("E-mail exist");
    }

    private Long deleteEmail(User user, Long emailId) {
        if (emailDataRepository.count() > 1) {
            EmailData emailData
                    = user.getEmailData()
                    .stream()
                    .filter(e -> Objects.equals(e.getId(), emailId))
                    .findFirst().orElseThrow(() -> new InvalidOperationException("E-mail not updated"));
            user.getEmailData().remove(emailData);
            userRepository.save(user);
            log.debug("email {} deleted", emailId);
            return emailId;
        }
        throw new InvalidOperationException("Last E-mail delete error");
    }

    private Long addPhoneToUser(User user, String phoneNumber) {
        long userCountWithPhone = phoneDataRepository.countByPhone(phoneNumber);
        if (userCountWithPhone == 0) {
            PhoneData phoneData = new PhoneData();
            phoneData.setUserId(user.getId());
            phoneData.setPhone(phoneNumber);
            user.getPhoneData().add(phoneData);
            log.debug("addPhoneToUser {} = {}", user.getId(), phoneNumber);
            return userRepository.save(user)
                    .getPhoneData()
                    .stream()
                    .filter(e -> e.getPhone().equals(phoneNumber))
                    .findFirst()
                    .orElseThrow(() -> new InvalidOperationException("Phone not added"))
                    .getId();
        }
        throw new InvalidOperationException("Phone exist");
    }

    private Long updateUserPhone(User user, Long phoneId, String phoneNumber) {
        long userCountWithPhone = phoneDataRepository.countByPhone(phoneNumber);
        if (userCountWithPhone == 0) {
            PhoneData phoneData
                    = user.getPhoneData()
                    .stream()
                    .filter(e -> Objects.equals(e.getId(), phoneId))
                    .findFirst().orElseThrow(() -> new InvalidOperationException("Phone not updated"));
            phoneData.setPhone(phoneNumber);
            userRepository.save(user);
            log.debug("updateUserPhone {}", phoneNumber);
            return phoneId;
        }
        throw new InvalidOperationException("Phone exist");
    }

    private Long deletePhone(User user, Long phoneId) {
        if (phoneDataRepository.count() > 1) {
            PhoneData phoneData
                    = user.getPhoneData()
                    .stream()
                    .filter(e -> Objects.equals(e.getId(), phoneId))
                    .findFirst().orElseThrow(() -> new InvalidOperationException("Phone not updated"));
            user.getPhoneData().remove(phoneData);
            userRepository.save(user);
            log.debug("Phone deleted: {}", phoneId);
            return phoneId;
        }
        throw new InvalidOperationException("Last Phone delete error");
    }
}
