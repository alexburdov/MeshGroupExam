package ru.alex.burdovitsin.mesh.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;
import ru.alex.burdovitsin.mesh.common.AbstractBaseTest;
import ru.alex.burdovitsin.mesh.common.OperationTypes;
import ru.alex.burdovitsin.mesh.config.security.JwtAuthenticationFilter;
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
    void phoneOperationGoodOne() throws Exception {
        PhoneOperation operation = new PhoneOperation();
        operation.setOperation(OperationTypes.CREATE);
        operation.setPhoneNumber("79207865431");

        String requestJson = objectWriter.writeValueAsString(operation);
        mvc.perform(put("/phone_operation")
                .header(JwtAuthenticationFilter.JWT_HEADER_NAME, getAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isOk());
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
    void emailOperationGoodOne() throws Exception {
        EmailOperation operation = new EmailOperation();
        operation.setOperation(OperationTypes.CREATE);
        operation.setEmail("tst@ts1t.tst");
        String requestJson = objectWriter.writeValueAsString(operation);
        mvc.perform(put("/email_operation")
                .header(JwtAuthenticationFilter.JWT_HEADER_NAME, getAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isOk());
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
    void getUserList() {
    }

    @Test
    void moneyTransfer() {
    }

    @Test
    void handleBadCredentialsException() {
    }
}