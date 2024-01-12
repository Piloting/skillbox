package ru.pilot.skillbox.socnet.users;


import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.pilot.skillbox.socnet.users.controller.dto.RegistrationInfo;
import ru.pilot.skillbox.socnet.users.controller.dto.UserDto;
import ru.pilot.skillbox.socnet.users.entity.Gender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("integTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
    
    @Container
    protected static final PostgreSQLContainer<PostgresContainerWrapper> postgresContainer = new PostgresContainerWrapper();

    @DynamicPropertySource
    public static void initSystemParams(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Test
    void healthTest(ApplicationContext context) throws Exception {
        assertThat(context).isNotNull();
        mockMvc
           .perform(MockMvcRequestBuilders.get("/actuator/health"))
           .andExpect(status().isOk());
    }
    
    @Test
    void checkExistsPhoneTest() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/user/checkExistsPhone")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .queryParam("phone", "123456")
                )
                .andReturn()
                .getResponse().getContentAsString();
        assertThat(response).isEqualTo("false");
    }
    
    @Test
    void addUserSuccessTest() throws Exception {
        // param new user
        RegistrationInfo registrationInfo = createRegistrationInfoDto();
        String json = objectMapper.writeValueAsString(registrationInfo);
        // post add
        MockHttpServletResponse responseAdd = postUser(json);
        // check result post
        assertThat(responseAdd.getStatus()).isEqualTo(200);
        String id = responseAdd.getContentAsString();

        // check - get new user by ID
        MockHttpServletResponse responseGet = mockMvc.perform(MockMvcRequestBuilders.get("/user/" + id)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                )
                .andReturn()
                .getResponse();
        
        // check get result
        assertThat(responseGet.getStatus()).isEqualTo(200);
        UserDto userDto = objectMapper.readValue(responseGet.getContentAsString(), UserDto.class);
        assertThat(userDto.getId()).isEqualTo(Long.parseLong(id));
        assertThat(userDto.getPhone()).isEqualTo(registrationInfo.getPhone());
        assertThat(userDto.getSurname()).isEqualTo(registrationInfo.getSurname());
    }
    
    @Test
    void addUserBadPhoneParamTest() throws Exception {
        // param new user
        RegistrationInfo registrationInfo = createRegistrationInfoDto();
        registrationInfo.setPhone(null);
        String json = objectMapper.writeValueAsString(registrationInfo);
        // post add
        MockHttpServletResponse responseAdd = postUser(json);
        // check result post
        assertThat(responseAdd.getStatus()).isEqualTo(400);
    }
    
    @Test
    void addUserBadEmailParamTest() throws Exception {
        // param new user
        RegistrationInfo registrationInfo = createRegistrationInfoDto();
        registrationInfo.setEmail("pam-pam");
        String json = objectMapper.writeValueAsString(registrationInfo);
        // post add
        MockHttpServletResponse responseAdd = postUser(json);
        // check result post
        assertThat(responseAdd.getStatus()).isEqualTo(400);
    }
    
    @Test
    void addUserDuplicateTest() throws Exception {
        // param new user
        RegistrationInfo registrationInfo = createRegistrationInfoDto();
        String json = objectMapper.writeValueAsString(registrationInfo);
        // post add
        MockHttpServletResponse responseAdd = postUser(json);
        // check result post
        assertThat(responseAdd.getStatus()).isEqualTo(200);
        // duplicate call
        responseAdd = postUser(json);
        // check result post
        assertThat(responseAdd.getStatus()).isEqualTo(400);
    }

    private MockHttpServletResponse postUser(String json) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andReturn()
                .getResponse();
    }

    private static RegistrationInfo createRegistrationInfoDto() {
        RegistrationInfo r = new RegistrationInfo();
        r.setEmail("Email@mail.ru");
        r.setPhone("9-999-999-99-99");
        r.setSurname("Surname");
        r.setFirstname("Firstname");
        r.setPatronymic("Patronymic");
        r.setGender(Gender.MALE);
        r.setBirthDate(LocalDateTime.now());
        r.setCity("City");
        r.setAvatarLink("AvatarLink");
        r.setInfo("Info");
        r.setHardSkills("HardSkills");
        return r;
    }
}
