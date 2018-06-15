package com.vladmeh.choosing.web;

import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.vladmeh.choosing.testdata.MenuTestData.MENU_0;
import static com.vladmeh.choosing.testdata.MenuTestData.getStringObjectMapMenu;
import static com.vladmeh.choosing.testdata.RestaurantTestData.RESTAURANT_0;
import static com.vladmeh.choosing.testdata.RestaurantTestData.RESTAURANT_2;
import static com.vladmeh.choosing.testdata.UserTestData.ADMIN;
import static com.vladmeh.choosing.testdata.UserTestData.USER;


public class MenuControllerTest extends AbstractControllerTest {
    private static final String MENU_URL = REST_URL + "/menu/";

    @Override
    public void getAll() throws Exception {
        ResultActions resultActions = testGetAll(MENU_URL, USER);
    }

    @Override
    public void getById() throws Exception {
        ResultActions resultActions = testGetById(MENU_URL + MENU_0.getId(), USER);
    }

    @Override
    public void getIsNotFound() throws Exception {
        ResultActions resultActions = testGetIsNotFound(MENU_URL + 100, USER);
    }

    @Override
    public void create() throws Exception {
        ResultActions resultActions = testCreate(MENU_URL, ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapMenu(RESTAURANT_0, LocalDate.now())));
    }

    @Override
    public void createIsConflict() throws Exception {
        Map<String, Object> created = new HashMap<>();
        created.put("date", LocalDate.now());
        created.put("restaurant", "http://api/restaurant/3");
        ResultActions resultActions = testCreateIsConflict(MENU_URL, ADMIN, objectMapper.writeValueAsString(created));
    }

    @Override
    public void createIsForbidden() throws Exception {
        ResultActions resultActions = testCreateIsForbidden(MENU_URL, USER,
                objectMapper.writeValueAsString(getStringObjectMapMenu(RESTAURANT_0, LocalDate.now())));
    }

    @Override
    public void update() throws Exception {
        ResultActions resultActions = testUpdate(MENU_URL + MENU_0.getId(), ADMIN,
                objectMapper.writeValueAsString(getStringObjectMapMenu(RESTAURANT_2, LocalDate.now())));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void updatedIsConflict() throws Exception {
        Map<String, Object> updated = new HashMap<>();
        updated.put("restaurant", "http://api/restaurant/3");

        ResultActions resultActions = testUpdateIsConflict(
                MENU_URL + MENU_0.getId(),
                ADMIN, objectMapper.writeValueAsString(updated));
    }

    @Override
    public void updateIsForbidden() throws Exception {
        ResultActions resultActions = testUpdateIsForbidden(MENU_URL + MENU_0.getId(), USER,
                objectMapper.writeValueAsString(getStringObjectMapMenu(RESTAURANT_2, LocalDate.now())));
    }

    @Override
    public void deleted() throws Exception {
        ResultActions resultActions = testDelete(MENU_URL + MENU_0.getId(), ADMIN);
    }

    @Override
    public void deletedIsForbidden() throws Exception {
        ResultActions resultActions = testDeleteIsForbidden(MENU_URL + MENU_0.getId(), USER);
    }
}
