package com.vladmeh.choosing.web;

import org.junit.Test;

import static com.vladmeh.choosing.testdata.DishesTestData.*;
import static com.vladmeh.choosing.testdata.MenuTestData.*;
import static com.vladmeh.choosing.testdata.UserTestData.ADMIN;
import static com.vladmeh.choosing.testdata.UserTestData.USER;
import static com.vladmeh.choosing.utils.TestUtil.userHttpBasic;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Dishes controller test.
 */
public class DishesControllerTest extends AbstractControllerTest {
    @Override
    public void getAll() throws Exception {
        testGetAll(DISHES_URL, USER);
    }

    @Override
    public void getById() throws Exception {
        testGetById(DISHES_URL + DISHES_0.getId(), USER);
    }

    @Override
    public void getIsNotFound() throws Exception {
        testGetIsNotFound(DISHES_URL + 100, USER);
    }

    @Test
    public void getByMenu() throws Exception {
        mockMvc.perform(get(DISHES_URL + "search/by-menu")
                .param("menu", MENU_URL + MENU_0.getId())
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getByDate() throws Exception {
        mockMvc.perform(get(DISHES_URL + "search/by-date")
                .param("date", "2018-05-23")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Override
    public void create() throws Exception {
        testCreate(DISHES_URL, ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapDishes("Завтрак", 200, MENU_1)));
    }


    @Override
    public void createIsConflict() throws Exception {
        testCreateIsConflict(DISHES_URL, ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapDishes(DISHES_10.getName(), 200, MENU_5)));
    }

    @Override
    public void createIsForbidden() throws Exception {
        testCreateIsForbidden(DISHES_URL, USER,
                objectMapper.writeValueAsString(getStringObjectMapDishes("Завтрак", 200, MENU_1)));
    }

    @Override
    public void update() throws Exception {
        testUpdate(DISHES_URL + DISHES_0.getId(), ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapDishes("Завтрак", 200)));
    }

    @Override
    public void updatedIsConflict() throws Exception {
        testUpdateIsConflict(DISHES_URL + DISHES_11.getId(), ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapDishes(DISHES_10.getName(), 100)));
    }

    @Override
    public void updateIsForbidden() throws Exception {
        testUpdateIsForbidden(DISHES_URL + DISHES_0.getId(), USER,
                objectMapper.writeValueAsString(getStringObjectMapDishes("Завтрак", 200)));
    }

    @Override
    public void deleted() throws Exception {
        testDelete(DISHES_URL + DISHES_0.getId(), ADMIN);
    }

    @Override
    public void deletedIsForbidden() throws Exception {
        testDeleteIsForbidden(DISHES_URL + DISHES_0.getId(), USER);
    }
}
