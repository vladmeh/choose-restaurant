package com.vladmeh.choosing.web;


import com.vladmeh.choosing.model.Role;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.vladmeh.choosing.utils.TestUtil.userHttpBasic;
import static com.vladmeh.choosing.testdata.UserTestData.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends AbstractControllerTest{

    private static final String USERS_URL = REST_URL + "/users/";

    @Test
    public void getUsers() throws Exception {
        this.mockMvc
                .perform(get(USERS_URL)
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserById() throws Exception {
        this.mockMvc
                .perform(get(USERS_URL + USER_ID)
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(USER.getName())))
                .andExpect(jsonPath("email", is(USER.getEmail())));
    }

    @Test
    public void getUserByEmail() throws Exception {
        this.mockMvc
                .perform(get(USERS_URL + "search/by-email")
                        .param("email", USER.getEmail())
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(USER.getName())))
                .andExpect(jsonPath("email", is(USER.getEmail())));
    }

    @Test
    public void getForbidden() throws Exception {
        mockMvc.perform(get(USERS_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void userIsNotFound() throws Exception {
        mockMvc.perform(get(USERS_URL + 2)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createUser() throws Exception {
        mockMvc.perform(post(USERS_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getCreatedUser())))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updatePutUser() throws Exception {
        mockMvc.perform(put(USERS_URL + USER_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(getUpdateUser())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updatePatchUser() throws Exception {
        Map<String, Object> update = new HashMap<>();
        update.put("name", "Update name");

        mockMvc.perform(patch(USERS_URL + USER_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(update)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void updateIsConflict() throws Exception {
        Map<String, Object> update = new HashMap<>();
        update.put("email", "admin@gmail.com");

        mockMvc.perform(patch(USERS_URL + USER_ID)
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

        mockMvc.perform(post(USERS_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void createIsForbidden() throws Exception {
        mockMvc.perform(post(USERS_URL)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getCreatedUser())))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deletedUser() throws Exception {
        mockMvc.perform(delete(USERS_URL + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
