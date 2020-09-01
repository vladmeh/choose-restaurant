package com.vladmeh.choosing.web;

import static com.vladmeh.choosing.testdata.LunchTestData.*;
import static com.vladmeh.choosing.testdata.RestaurantTestData.RESTAURANT_0;
import static com.vladmeh.choosing.testdata.RestaurantTestData.RESTAURANT_2;
import static com.vladmeh.choosing.testdata.UserTestData.ADMIN;
import static com.vladmeh.choosing.testdata.UserTestData.USER;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 29.06.2018.
 * @link https://github.com/vladmeh/choose-restaurant
 */

class LunchControllerTest extends AbstractControllerTest {

    @Override
    void getAll() throws Exception {
        testGetAll(LUNCH_URL, USER);
    }

    @Override
    void getById() throws Exception {
        testGetById(LUNCH_URL + LUNCH_0.getId(), USER);
    }

    @Override
    void getIsNotFound() throws Exception {
        testGetIsNotFound(LUNCH_URL + 100, USER);
    }

    @Override
    void create() throws Exception {
        testCreate(LUNCH_URL, ADMIN,
                objectMapper.writeValueAsString(
                        getStringObjectMapLunch(RESTAURANT_0, LOCAL_DATE, "New lunch", 200)));
    }

    @Override
    void createIsConflict() throws Exception {
        testCreateIsConflict(LUNCH_URL, ADMIN,
                objectMapper.writeValueAsString(
                        getStringObjectMapLunch(RESTAURANT_0, LOCAL_DATE, "Kebab", 200)
                ));
    }

    @Override
    void createIsForbidden() throws Exception {
        testCreateIsForbidden(LUNCH_URL, USER,
                objectMapper.writeValueAsString(
                        getStringObjectMapLunch(RESTAURANT_0, LOCAL_DATE, "New lunch", 200)));
    }

    @Override
    void update() throws Exception {
        testUpdate(LUNCH_URL + LUNCH_0.getId(), ADMIN,
                objectMapper.writeValueAsString(
                        getStringObjectMapLunch(RESTAURANT_2, LOCAL_DATE, "Update lunch", 100)
                ));
    }

    @Override
    void updatedIsConflict() throws Exception {
        testUpdateIsConflict(LUNCH_URL + LUNCH_0.getId(), ADMIN,
                objectMapper.writeValueAsString(
                        getStringObjectMapLunch(RESTAURANT_0, LOCAL_DATE, "Kebab", 100)
                ));
    }

    @Override
    void updateIsForbidden() throws Exception {
        testUpdateIsForbidden(LUNCH_URL + LUNCH_0.getId(), USER,
                objectMapper.writeValueAsString(
                        getStringObjectMapLunch(RESTAURANT_2, LOCAL_DATE, "Kebab", 100)
                ));
    }

    @Override
    void deleted() throws Exception {
        testDelete(LUNCH_URL + LUNCH_0.getId(), ADMIN);
    }

    @Override
    void deletedIsForbidden() throws Exception {
        testDeleteIsForbidden(LUNCH_URL + LUNCH_0.getId(), USER);
    }
}
