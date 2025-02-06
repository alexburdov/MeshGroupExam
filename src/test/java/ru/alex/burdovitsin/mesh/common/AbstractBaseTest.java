package ru.alex.burdovitsin.mesh.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.util.Strings;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.alex.burdovitsin.mesh.config.security.JwtAuthenticationFilter;
import ru.alex.burdovitsin.mesh.model.rest.JwtRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AbstractBaseTest {

    private static final String DEFAULT_AUTH_PATH = "/auth/token";

    private static final String DEFAULT_USER_NAME = "user";

    private static final String DEFAULT_USER_PASSWORD = "password";

    private static final String TOKEN_TAG = "token";

    protected final MockMvc mvc;

    protected final ObjectWriter objectWriter;
    protected final ObjectMapper objectMapper;

    private String token;

    public AbstractBaseTest(WebApplicationContext context) {
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        this.objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
    }

    protected String getAuthToken() throws Exception {
        if (Strings.isBlank(token)) { // В тестах потокозащищеность не нужна - простой синглетон
            token = createAuthToken();
        }
        return token;
    }

    private String createAuthToken() throws Exception {
        JwtRequest request = new JwtRequest(DEFAULT_USER_NAME, DEFAULT_USER_PASSWORD);
        String requestJson = objectWriter.writeValueAsString(request);
        MvcResult result = mvc.perform(post(DEFAULT_AUTH_PATH).contentType(APPLICATION_JSON)
                .content(requestJson)).andReturn();

        String content = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(content);
        String token = jsonNode.get(TOKEN_TAG).asText();
        return JwtAuthenticationFilter.BEARER_PREFIX + token;
    }

}
