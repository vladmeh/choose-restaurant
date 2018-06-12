package com.vladmeh.choosing.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmeh.choosing.model.Role;
import com.vladmeh.choosing.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.vladmeh.choosing.TestUtil.userHttpBasic;
import static com.vladmeh.choosing.UserTestData.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class UserControllerTest {

    private static final String REST_URL = "/api/users/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUsers() throws Exception {
        this.mockMvc
                .perform(get(REST_URL)
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserById() throws Exception {
        this.mockMvc
                .perform(get(REST_URL + USER_ID)
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(USER.getName())))
                .andExpect(jsonPath("email", is(USER.getEmail())));
    }

    @Test
    public void getUserByEmail() throws Exception {
        this.mockMvc
                .perform(get(REST_URL + "search/by-email")
                        .param("email", USER.getEmail())
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(USER.getName())))
                .andExpect(jsonPath("email", is(USER.getEmail())));
    }

    @Test
    public void getForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void userIsNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 2)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createUser() throws Exception {
        mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getCreated())))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updatePutUser() throws Exception {
        mockMvc.perform(put(REST_URL + USER_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(getUpdate())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updatePatchUser() throws Exception {
        Map<String, Object> update = new HashMap<>();
        update.put("name", "Update name");

        mockMvc.perform(patch(REST_URL + USER_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(update)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateIsConflict() throws Exception {
        Map<String, Object> update = new HashMap<>();
        update.put("email", "admin@gmail.com");

        mockMvc.perform(patch(REST_URL + USER_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(update)))
                .andDo(print())
                .andExpect(status().isConflict());
    }


    @Test
    public void createIsConflict() throws Exception {
        Map<String, Object> created = new HashMap<>();
        created.put("name", "New user");
        created.put("email", "user@yandex.ru");
        created.put("password", "password");
        created.put("roles", Collections.singleton(Role.ROLE_USER));

        mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void createIsForbidden() throws Exception {
        mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getCreated())))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deletedUser() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
