package ru.alex.burdovitsin.mesh.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import ru.alex.burdovitsin.mesh.common.AbstractBaseTest;
import ru.alex.burdovitsin.mesh.model.rest.JwtRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class JwtAuthenticationControllerTest extends AbstractBaseTest {

    public JwtAuthenticationControllerTest(WebApplicationContext context) {
        super(context);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createAuthenticationToken() throws Exception {
        String url = "/auth/token";
        JwtRequest request = new JwtRequest("user", "password");
        String requestJson = objectWriter.writeValueAsString(request);

        mvc.perform(post(url).contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void createAuthenticationTokenNegative() throws Exception {
        String url = "/auth/token";
        JwtRequest request = new JwtRequest("no-user", "any");
        String requestJson = objectWriter.writeValueAsString(request);

        Assertions.assertThrows(NestedServletException.class,
                () -> mvc.perform(post(url).contentType(APPLICATION_JSON).content(requestJson)).andReturn());
    }

}