package com.vladmeh.choosing.web;

import org.junit.Test;

import java.time.LocalDate;

import static com.vladmeh.choosing.testdata.MenuTestData.*;
import static com.vladmeh.choosing.testdata.RestaurantTestData.*;
import static com.vladmeh.choosing.testdata.UserTestData.ADMIN;
import static com.vladmeh.choosing.testdata.UserTestData.USER;
import static com.vladmeh.choosing.utils.TestUtil.userHttpBasic;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MenuControllerTest extends AbstractControllerTest {
    @Override
    public void getAll() throws Exception {
        testGetAll(MENU_URL, USER);
    }

    @Override
    public void getById() throws Exception {
        testGetById(MENU_URL + MENU_0.getId(), USER);
    }

    @Override
    public void getIsNotFound() throws Exception {
        testGetIsNotFound(MENU_URL + 100, USER);
    }

    @Test
    public void getMenuByDate() throws Exception {
        mockMvc.perform(get(MENU_URL + "search/by-date")
                .param("date", "2018-05-23")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getMenuByRestaurant() throws Exception {
        mockMvc.perform(get(MENU_URL + "search/by-restaurant")
                .param("restaurant", RESTAURANT_URL + RESTAURANT_0.getId())
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Override
    public void create() throws Exception {
        testCreate(MENU_URL, ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapMenu(RESTAURANT_0, LocalDate.now())));
    }

    @Test
    public void createIsConflict() throws Exception {
        testCreateIsConflict(MENU_URL, ADMIN, objectMapper.writeValueAsString(getStringObjectMapMenu(3)));
    }

    @Override
    public void createIsForbidden() throws Exception {
        testCreateIsForbidden(MENU_URL, USER,
                objectMapper.writeValueAsString(getStringObjectMapMenu(RESTAURANT_0, LocalDate.now())));
    }

    @Override
    public void update() throws Exception {
        testUpdate(MENU_URL + MENU_0.getId(), ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapMenu(RESTAURANT_2, LocalDate.now())));
    }

    @Test
    public void updatedIsConflict() throws Exception {
        testUpdateIsConflict(
                MENU_URL + MENU_0.getId(),
                ADMIN, objectMapper.writeValueAsString(getStringObjectMapMenu(3)));
    }

    @Override
    public void updateIsForbidden() throws Exception {
        testUpdateIsForbidden(MENU_URL + MENU_0.getId(), USER,
                objectMapper.writeValueAsString(getStringObjectMapMenu(RESTAURANT_2, LocalDate.now())));
    }

    @Override
    public void deleted() throws Exception {
        testDelete(MENU_URL + MENU_0.getId(), ADMIN);
    }

    @Override
    public void deletedIsForbidden() throws Exception {
        testDeleteIsForbidden(MENU_URL + MENU_0.getId(), USER);
    }
}
