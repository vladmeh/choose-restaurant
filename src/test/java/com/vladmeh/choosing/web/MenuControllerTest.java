package com.vladmeh.choosing.web;

import com.vladmeh.choosing.model.Menu;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.vladmeh.choosing.testdata.MenuTestData.MENU_0;
import static com.vladmeh.choosing.testdata.MenuTestData.getStringObjectMapMenu;
import static com.vladmeh.choosing.testdata.RestaurantTestData.RESTAURANT_0;
import static com.vladmeh.choosing.testdata.UserTestData.ADMIN;
import static com.vladmeh.choosing.utils.TestUtil.userHttpBasic;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MenuControllerTest extends AbstractControllerTest {
    private static final String MENU_URL = REST_URL + "/menu/";

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createMenu() throws Exception {
        mockMvc.perform(post(MENU_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getStringObjectMapMenu(RESTAURANT_0, LocalDate.now()))))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updateMenu() throws Exception {
        mockMvc.perform(put(MENU_URL+MENU_0.getId())
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getStringObjectMapMenu(RESTAURANT_0, LocalDate.now()))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateIsBadRequest() throws Exception {
        Map<String, Object> update = new HashMap<>();
        update.put("restaurant", "http://api/restaurant/3");
        mockMvc.perform(patch(MENU_URL+MENU_0.getId())
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(update)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
