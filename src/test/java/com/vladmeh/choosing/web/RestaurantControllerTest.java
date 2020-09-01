package com.vladmeh.choosing.web;

import org.junit.jupiter.api.Test;

import static com.vladmeh.choosing.testdata.RestaurantTestData.*;
import static com.vladmeh.choosing.testdata.UserTestData.ADMIN;
import static com.vladmeh.choosing.testdata.UserTestData.USER;
import static com.vladmeh.choosing.utils.TestUtil.userHttpBasic;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {
    @Override
    void getAll() throws Exception {
        testGetAll(RESTAURANT_URL, USER);
    }

    @Override
    void getById() throws Exception {
        testGetById(RESTAURANT_URL + RESTAURANT_0.getId(), USER);
    }

    @Override
    void getIsNotFound() throws Exception {
        testGetIsNotFound(RESTAURANT_URL + 100, USER);
    }

    @Test
    void getRestaurantByName() throws Exception {
        mockMvc.perform(get(RESTAURANT_URL + "search/by-name")
                .param("name", RESTAURANT_0.getName())
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(RESTAURANT_0.getName())));
    }

    @Override
    void create() throws Exception {
        testCreate(RESTAURANT_URL, ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant("Теремок")));
    }

    @Override
    void createIsConflict() throws Exception {
        testCreateIsConflict(RESTAURANT_URL, ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant(RESTAURANT_2.getName())));
    }

    @Override
    void createIsForbidden() throws Exception {
        testCreateIsForbidden(RESTAURANT_URL, USER,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant("Теремок")));
    }

    @Override
    void update() throws Exception {
        testUpdate(RESTAURANT_URL + RESTAURANT_0.getId(), ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant("Теремок")));
    }

    @Override
    void updatedIsConflict() throws Exception {
        testUpdateIsConflict(RESTAURANT_URL + RESTAURANT_0.getId(), ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant(RESTAURANT_2.getName())));

    }

    @Override
    void updateIsForbidden() throws Exception {
        testUpdateIsForbidden(RESTAURANT_URL + RESTAURANT_0.getId(), USER,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant("Теремок")));
    }

    @Override
    void deleted() throws Exception {
        testDelete(RESTAURANT_URL + RESTAURANT_1.getId(), ADMIN);
    }

    @Override
    void deletedIsForbidden() throws Exception {
        testDeleteIsForbidden(RESTAURANT_URL + RESTAURANT_1.getId(), USER);
    }
}
