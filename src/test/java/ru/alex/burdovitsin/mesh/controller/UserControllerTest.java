package ru.alex.burdovitsin.mesh.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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

@SpringBootTest
@ActiveProfiles("test")
class UserControllerTest extends AbstractBaseTest {

    public UserControllerTest(WebApplicationContext context) {
        super(context);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void echoTest() throws Exception {
        String token = getAuthToken();
        mvc.perform(get("/echo").param(JwtAuthenticationFilter.HEADER_NAME, token))
                .andExpect(content().string("ECHO"));
    }

    @Test
    void emailOperation() {
    }

    @Test
    void phoneOperationGoodOne() throws Exception {
        PhoneOperation operation = new PhoneOperation();
        long generatedLong = new Random().nextLong();
        operation.setUserId(generatedLong);
        operation.setOperation(OperationTypes.CREATE);
        operation.setPhoneNumber("79207865432");

        String requestJson = objectWriter.writeValueAsString(operation);
        mvc.perform(put("/phone_operation")
                .param(JwtAuthenticationFilter.HEADER_NAME, getAuthToken())
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
        operation.setPhoneNumber("79207865432");

        String requestJson = objectWriter.writeValueAsString(operation);
        mvc.perform(put("/phone_operation")
                .param(JwtAuthenticationFilter.HEADER_NAME, getAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is(422));
    }

    @Test
    void emailOperationGoodOne() throws Exception {
        EmailOperation operation = new EmailOperation();
        long generatedLong = new Random().nextLong();
        operation.setUserId(generatedLong);
        operation.setOperation(OperationTypes.CREATE);
        operation.setEmail("tst@tst.tst");

        String requestJson = objectWriter.writeValueAsString(operation);
        mvc.perform(put("/email_operation")
                .param(JwtAuthenticationFilter.HEADER_NAME, getAuthToken())
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
                .param(JwtAuthenticationFilter.HEADER_NAME, getAuthToken())
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