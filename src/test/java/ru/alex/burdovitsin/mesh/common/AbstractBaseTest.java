package ru.alex.burdovitsin.mesh.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.alex.burdovitsin.mesh.config.security.JwtAuthenticationFilter;
import ru.alex.burdovitsin.mesh.model.jpa.Account;
import ru.alex.burdovitsin.mesh.model.jpa.EmailData;
import ru.alex.burdovitsin.mesh.model.jpa.PhoneData;
import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.model.rest.JwtRequest;
import ru.alex.burdovitsin.mesh.repository.AccountRepository;
import ru.alex.burdovitsin.mesh.repository.EmailDataRepository;
import ru.alex.burdovitsin.mesh.repository.PhoneDataRepository;
import ru.alex.burdovitsin.mesh.repository.UserRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ActiveProfiles("test")
@ComponentScan(basePackages = {"ru.alex.burdovitsin.mesh"})
public class AbstractBaseTest {

    protected static final String DEFAULT_AUTH_PATH = "/auth/token";

    protected static final String DEFAULT_USER_NAME = "user";

    protected static final String DEFAULT_USER_PASSWORD = "password";

    protected static final String DEFAULT_CODE_USER_PASSWORD = "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";

    protected static final String TOKEN_TAG = "token";

    protected final MockMvc mvc;

    protected final ObjectWriter objectWriter;

    protected final ObjectMapper objectMapper;

    private String token;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected PhoneDataRepository phoneDataRepository;

    @Autowired
    protected EmailDataRepository emailDataRepository;

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

    protected void createMainUser() {
        User user = createUserOne();
        userRepository.saveAndFlush(user);
    }


    protected void clearAllData() {
        userRepository.deleteAll();
        accountRepository.deleteAll();
        phoneDataRepository.deleteAll();
        emailDataRepository.deleteAll();
    }

    protected User createUserOne() {
        User user = new User();
        user.setId(1L);
        user.setUsername(DEFAULT_USER_NAME);
        user.setPassword(DEFAULT_CODE_USER_PASSWORD);
        user.setDateOfBird(Date.valueOf("1990-02-10"));
        Account account = new Account();
        account.setBalance(BigDecimal.TEN);
        account.setInitBalance(BigDecimal.TEN);
        user.setAccount(account);
        List<PhoneData> phones = new ArrayList<>();
        PhoneData phone = new PhoneData();
        phone.setPhone("79207865432");
        phones.add(phone);
        user.setPhoneData(phones);

        List<EmailData> emails = new ArrayList<>();
        EmailData email = new EmailData();
        email.setEmail("tst@tst.tst");
        emails.add(email);
        user.setEmailData(emails);
        return user;
    }
}
