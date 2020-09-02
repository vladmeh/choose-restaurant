package com.vladmeh.choosing.web;


import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.vladmeh.choosing.testdata.UserTestData.*;
import static com.vladmeh.choosing.utils.TestUtil.userHttpBasic;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends AbstractControllerTest {

    @Override
    @Test
    void getAll() throws Exception {
        testGetAll(USERS_URL, ADMIN);
    }

    @Override
    @Test
    void getById() throws Exception {
        testGetById(USERS_URL + USER_ID, ADMIN)
                .andExpect(jsonPath("name", is(USER.getName())))
                .andExpect(jsonPath("email", is(USER.getEmail())));
    }

    @Test
    void getUserByEmail() throws Exception {
        mockMvc
                .perform(get(USERS_URL + "search/by-email")
                        .param("email", USER.getEmail())
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(USER.getName())))
                .andExpect(jsonPath("email", is(USER.getEmail())));
    }

    @Test
    void getIsForbidden() throws Exception {
        testGetIsForbidden(USERS_URL, USER);
    }

    @Override
    @Test
    void getIsNotFound() throws Exception {
        testGetIsNotFound(USERS_URL + 2, ADMIN);
    }

    @Override
    @Test
    void create() throws Exception {
        testCreate(USERS_URL, ADMIN, objectMapper.writeValueAsString(getCreatedUser()));
    }

    @Override
    @Test
    void createIsConflict() throws Exception {
        Map<String, Object> created = getCreatedUser();
        created.put("email", "user@yandex.ru");

        testCreateIsConflict(USERS_URL, ADMIN, objectMapper.writeValueAsString(created));
    }

    @Override
    @Test
    void createIsForbidden() throws Exception {
        testCreateIsForbidden(USERS_URL, USER, objectMapper.writeValueAsString(getCreatedUser()));
    }

    @Override
    @Test
    void update() throws Exception {
        testUpdate(USERS_URL + USER_ID, ADMIN, objectMapper.writeValueAsString(getUpdateUser()));
    }

    @Override
    void updatedIsConflict() throws Exception {
        Map<String, Object> update = getUpdateUser();
        update.put("email", "admin@gmail.com");

        testUpdateIsConflict(USERS_URL + USER_ID, ADMIN, objectMapper.writeValueAsString(update));

    }

    @Override
    @Test
    void updateIsForbidden() throws Exception {
        testUpdateIsForbidden(USERS_URL + USER_ID, USER, objectMapper.writeValueAsString(getUpdateUser()));
    }

    @Override
    @Test
    void deleted() throws Exception {
        testDelete(USERS_URL + USER_ID, ADMIN);
    }

    @Override
    @Test
    void deletedIsForbidden() throws Exception {
        testDeleteIsForbidden(USERS_URL + USER_ID, USER);
    }
}
