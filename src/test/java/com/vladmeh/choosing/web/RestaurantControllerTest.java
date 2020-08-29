package com.vladmeh.choosing.web;

import org.junit.Test;

import static com.vladmeh.choosing.testdata.RestaurantTestData.*;
import static com.vladmeh.choosing.testdata.UserTestData.ADMIN;
import static com.vladmeh.choosing.testdata.UserTestData.USER;
import static com.vladmeh.choosing.utils.TestUtil.userHttpBasic;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantControllerTest extends AbstractControllerTest {
    @Override
    public void getAll() throws Exception {
        testGetAll(RESTAURANT_URL, USER);
    }

    @Override
    public void getById() throws Exception {
        testGetById(RESTAURANT_URL + RESTAURANT_0.getId(), USER);
    }

    @Override
    public void getIsNotFound() throws Exception {
        testGetIsNotFound(RESTAURANT_URL + 100, USER);
    }

    @Test
    public void getRestaurantByName() throws Exception {
        mockMvc.perform(get(RESTAURANT_URL + "search/by-name")
                .param("name", RESTAURANT_0.getName())
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(RESTAURANT_0.getName())));
    }

    @Override
    public void create() throws Exception {
        testCreate(RESTAURANT_URL, ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant("Теремок")));
    }

    @Override
    public void createIsConflict() throws Exception {
        testCreateIsConflict(RESTAURANT_URL, ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant(RESTAURANT_2.getName())));
    }

    @Override
    public void createIsForbidden() throws Exception {
        testCreateIsForbidden(RESTAURANT_URL, USER,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant("Теремок")));
    }

    @Override
    public void update() throws Exception {
        testUpdate(RESTAURANT_URL + RESTAURANT_0.getId(), ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant("Теремок")));
    }

    @Override
    public void updatedIsConflict() throws Exception {
        testUpdateIsConflict(RESTAURANT_URL + RESTAURANT_0.getId(), ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant(RESTAURANT_2.getName())));

    }

    @Override
    public void updateIsForbidden() throws Exception {
        testUpdateIsForbidden(RESTAURANT_URL + RESTAURANT_0.getId(), USER,
                objectMapper.writeValueAsString(getStringObjectMapRestaurant("Теремок")));
    }

    @Override
    public void deleted() throws Exception {
        testDelete(RESTAURANT_URL + RESTAURANT_1.getId(), ADMIN);
    }

    @Override
    public void deletedIsForbidden() throws Exception {
        testDeleteIsForbidden(RESTAURANT_URL + RESTAURANT_1.getId(), USER);
    }
}
