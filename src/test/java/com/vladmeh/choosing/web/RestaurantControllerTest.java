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
    @Test
    void getAll() throws Exception {
        testGetAll(RESTAURANT_URL, USER);
    }

    @Override
    @Test
    void getById() throws Exception {
        testGetById(RESTAURANT_URL + RESTAURANT_0.getId(), USER);
    }

    @Override
    @Test
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
    @Test
    void create() throws Exception {
        testCreate(RESTAURANT_URL, ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant("Теремок")));
    }

    @Override
    @Test
    void createIsConflict() throws Exception {
        testCreateIsConflict(RESTAURANT_URL, ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant(RESTAURANT_2.getName())));
    }

    @Override
    @Test
    void createIsForbidden() throws Exception {
        testCreateIsForbidden(RESTAURANT_URL, USER,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant("Теремок")));
    }

    @Override
    @Test
    void update() throws Exception {
        testUpdate(RESTAURANT_URL + RESTAURANT_0.getId(), ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant("Теремок")));
    }

    @Override
    @Test
    void updatedIsConflict() throws Exception {
        testUpdateIsConflict(RESTAURANT_URL + RESTAURANT_0.getId(), ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant(RESTAURANT_2.getName())));

    }

    @Override
    @Test
    void updateIsForbidden() throws Exception {
        testUpdateIsForbidden(RESTAURANT_URL + RESTAURANT_0.getId(), USER,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant("Теремок")));
    }

    @Override
    @Test
    void deleted() throws Exception {
        testDelete(RESTAURANT_URL + RESTAURANT_1.getId(), ADMIN);
    }

    @Override
    @Test
    void deletedIsForbidden() throws Exception {
        testDeleteIsForbidden(RESTAURANT_URL + RESTAURANT_1.getId(), USER);
    }
}
