package ru.alex.burdovitsin.mesh.services.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.alex.burdovitsin.mesh.common.AbstractBaseTest;
import ru.alex.burdovitsin.mesh.model.rest.UserItem;
import ru.alex.burdovitsin.mesh.model.rest.UserSeekRequest;
import ru.alex.burdovitsin.mesh.services.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceImplTest extends AbstractBaseTest {

    @Autowired
    private UserService userService;

    public UserServiceImplTest(WebApplicationContext context) {
        super(context);
    }

    @BeforeEach
    void setUp() {
        clearAllData();
        userRepository.save(createUserOne());
        userRepository.save(createUserTwo());
        userRepository.save(createUserThree());
        userRepository.save(createUserFour());
        userRepository.save(createUserFive());
        userRepository.save(createUserSix());
    }

    @AfterEach
    void tearDown() {
        clearAllData();
    }

    @Test
    @Transactional
    void getUserList() {
        UserSeekRequest request = new UserSeekRequest();
        List<UserItem> userItems = userService.getUserList(request);
        assertNotNull(userItems);
        assertEquals(6, userItems.size());
    }

    @Test
    @Transactional
    void getUserListPagingOne() {
        UserSeekRequest request = new UserSeekRequest();
        request.setPageSize(2);
        request.setPageOffset(0);
        List<UserItem> userItems = userService.getUserList(request);
        assertNotNull(userItems);
        assertEquals(2, userItems.size());
    }

    @Test
    void moneyTransfer() {
    }
}