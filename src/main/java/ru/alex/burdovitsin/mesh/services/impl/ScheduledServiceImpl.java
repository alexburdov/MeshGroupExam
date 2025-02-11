package ru.alex.burdovitsin.mesh.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.services.ScheduledService;
import ru.alex.burdovitsin.mesh.services.UserService;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ScheduledServiceImpl implements ScheduledService {

    private final UserService userService;

    public ScheduledServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Async
    @Scheduled(cron = "${scheduler.increase-balance-cron}")
    public void autoIncreaseBalance() {
        log.info("Auto increase-balance started in {}", LocalDateTime.now());
        for (User user: userService.getUserForIncreaseBalance()) {
            userService.increaseAccountBalanceIfNeeded(user);
        }
        log.info("Auto increase-balance ended in {}", LocalDateTime.now());
    }
}
