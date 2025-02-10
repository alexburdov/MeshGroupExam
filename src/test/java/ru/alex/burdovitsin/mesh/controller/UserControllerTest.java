package ru.alex.burdovitsin.mesh.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.alex.burdovitsin.mesh.common.AbstractBaseTest;
import ru.alex.burdovitsin.mesh.common.OperationTypes;
import ru.alex.burdovitsin.mesh.config.security.JwtAuthenticationFilter;
import ru.alex.burdovitsin.mesh.exception.InvalidOperationException;
import ru.alex.burdovitsin.mesh.model.jpa.EmailData;
import ru.alex.burdovitsin.mesh.model.jpa.PhoneData;
import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.model.rest.EmailOperation;
import ru.alex.burdovitsin.mesh.model.rest.PhoneOperation;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractBaseTest {

    public UserControllerTest(WebApplicationContext context) {
        super(context);
    }

    @BeforeEach
    void setUp() {
        clearAllData();
        createMainUser();
    }

    @AfterEach
    void tearDown() {
        clearAllData();
    }

    @Test
    public void echoTest() throws Exception {
        String token = getAuthToken();
        mvc.perform(get("/echo").header(JwtAuthenticationFilter.JWT_HEADER_NAME, token))
                .andExpect(content().string("ECHO user"));
    }

    @Test
    void emailOperationGoodOne() throws Exception {
        EmailOperation operation = new EmailOperation();
        operation.setOperation(OperationTypes.CREATE);
        operation.setEmail("tst@ts1t.tst");
        String requestJson = objectWriter.writeValueAsString(operation);
        MvcResult result = mvc.perform(put("/email_operation")
                .header(JwtAuthenticationFilter.JWT_HEADER_NAME, getAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertTrue(Long.parseLong(result.getResponse().getContentAsString()) > 0L);
    }

    @Test
    @Transactional
    void emailOperationGoodUpdateOne() throws Exception {
        User user = getDefaultUser();
        long emailId = user.getEmailData().get(0).getId();
        EmailOperation operation = new EmailOperation();
        operation.setOperation(OperationTypes.UPDATE);
        operation.setEmailId(emailId);
        String actualEmail = "tst@ts1t.upd";
        operation.setEmail(actualEmail);

        String requestJson = objectWriter.writeValueAsString(operation);
        MvcResult result = mvc.perform(put("/email_operation")
                .header(JwtAuthenticationFilter.JWT_HEADER_NAME, getAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(emailId, Long.parseLong(result.getResponse().getContentAsString()));
        User updatedUser = getDefaultUser();
        EmailData updatedEmail = updatedUser
                .getEmailData()
                .stream()
                .filter(e -> e.getEmail().equals(actualEmail))
                .findAny().orElseThrow(InvalidOperationException::new);
        Assertions.assertEquals(emailId, updatedEmail.getId());
        Assertions.assertEquals(updatedEmail.getEmail(), actualEmail);
    }

    @Test
    @Transactional
    void emailOperationGoodDeleteOne() throws Exception {
        User user = getDefaultUser();
        String actualEmail = "tst@ts1t.del";
        user.getEmailData().addAll(createEmailData(actualEmail));
        userRepository.save(user);
        user = getDefaultUser();
        long emailId = user.getEmailData().get(0).getId();
        String deletedEmail = user.getEmailData().get(0).getEmail();
        EmailOperation operation = new EmailOperation();
        operation.setOperation(OperationTypes.DELETE);
        operation.setEmailId(emailId);

        String requestJson = objectWriter.writeValueAsString(operation);
        MvcResult result = mvc.perform(put("/email_operation")
                .header(JwtAuthenticationFilter.JWT_HEADER_NAME, getAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(emailId, Long.parseLong(result.getResponse().getContentAsString()));
        User updatedUser = getDefaultUser();

        Assertions.assertEquals(1, updatedUser.getEmailData().size());
        Assertions.assertNotEquals(updatedUser.getEmailData().get(0).getEmail(), deletedEmail);
    }

    @Test
    void emailOperationBadOne() throws Exception {
        EmailOperation operation = new EmailOperation();
        long generatedLong = new Random().nextLong();
        operation.setEmailId(generatedLong);
        operation.setOperation(OperationTypes.CREATE);
        operation.setEmail("any");

        String requestJson = objectWriter.writeValueAsString(operation);
        mvc.perform(put("/email_operation")
                .header(JwtAuthenticationFilter.JWT_HEADER_NAME, getAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is(422));
    }

    @Test
    void phoneOperationGoodOne() throws Exception {
        PhoneOperation operation = new PhoneOperation();
        operation.setOperation(OperationTypes.CREATE);
        String phoneNumber = "71107865431";
        operation.setPhoneNumber(phoneNumber);
        String requestJson = objectWriter.writeValueAsString(operation);
        MvcResult result = mvc.perform(put("/phone_operation")
                .header(JwtAuthenticationFilter.JWT_HEADER_NAME, getAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertTrue(Long.parseLong(result.getResponse().getContentAsString()) > 0L);
    }

    @Test
    @Transactional
    void phoneOperationGoodUpdateOne() throws Exception {
        User user = getDefaultUser();
        long phoneId = user.getPhoneData().get(0).getId();
        PhoneOperation operation = new PhoneOperation();
        operation.setOperation(OperationTypes.UPDATE);
        operation.setPhoneId(phoneId);
        String actualPhone = "71107865430";
        operation.setPhoneNumber(actualPhone);

        String requestJson = objectWriter.writeValueAsString(operation);
        MvcResult result = mvc.perform(put("/phone_operation")
                .header(JwtAuthenticationFilter.JWT_HEADER_NAME, getAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(phoneId, Long.parseLong(result.getResponse().getContentAsString()));
        User updatedUser = getDefaultUser();
        PhoneData updatedPhone = updatedUser
                .getPhoneData()
                .stream()
                .filter(e -> e.getPhone().equals(actualPhone))
                .findAny().orElseThrow(InvalidOperationException::new);
        Assertions.assertEquals(phoneId, updatedPhone.getId());
        Assertions.assertEquals(updatedPhone.getPhone(), actualPhone);
    }

    @Test
    @Transactional
    void phoneOperationGoodDeleteOne() throws Exception {
        User user = getDefaultUser();
        String actualPhone = "11107865430";
        user.getPhoneData().addAll(createPhoneData(actualPhone));
        userRepository.save(user);
        user = getDefaultUser();
        long phoneId = user.getPhoneData().get(0).getId();
        String deletedPhone = user.getPhoneData().get(0).getPhone();
        PhoneOperation operation = new PhoneOperation();
        operation.setOperation(OperationTypes.DELETE);
        operation.setPhoneId(phoneId);

        String requestJson = objectWriter.writeValueAsString(operation);
        MvcResult result = mvc.perform(put("/phone_operation")
                .header(JwtAuthenticationFilter.JWT_HEADER_NAME, getAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(phoneId, Long.parseLong(result.getResponse().getContentAsString()));
        User updatedUser = getDefaultUser();

        Assertions.assertEquals(1, updatedUser.getPhoneData().size());
        Assertions.assertNotEquals(updatedUser.getPhoneData().get(0).getPhone(), deletedPhone);
    }

    @Test
    void phoneOperationBadOne() throws Exception {
        PhoneOperation operation = new PhoneOperation();
        long generatedLong = new Random().nextLong();
        operation.setPhoneId(generatedLong);
        operation.setOperation(OperationTypes.CREATE);
        operation.setPhoneNumber("207865432");

        String requestJson = objectWriter.writeValueAsString(operation);
        mvc.perform(put("/phone_operation")
                .header(JwtAuthenticationFilter.JWT_HEADER_NAME, getAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is(422));
    }

    @Test
    void getUserList() {
    }

    @Test
    void moneyTransfer() {
    }

    @Test
    void handleBadCredentialsException() {
    }
}