package com.vladmeh.choosing.web;

import com.vladmeh.choosing.model.Choice;
import com.vladmeh.choosing.repository.ChoiceRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.vladmeh.choosing.RestaurantTestData.RESTAURANT_0;
import static com.vladmeh.choosing.TestUtil.userAuth;
import static com.vladmeh.choosing.UserTestData.USER;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 06.06.2018.
 * @link https://github.com/vladmeh/choosing-topjava
 * AutoConfigureRestDocs - https://habr.com/post/341636/
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// @TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class ChoiceControllerTest {

    private static final LocalTime TIME_LIMIT = LocalTime.parse("11:00");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChoiceRepository choiceRepository;

    @After
    public void tearDown() {
        choiceRepository.deleteAll();
    }

    @Test
    public void currentIsNotFount() throws Exception {
        ResultActions actions = mockMvc.perform(get("/api/choice")
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(status().isNotFound());

        actions.andDo(document("{class-name}/{method-name}"));
    }

    @Test
    public void choiceCreate() throws Exception {
        ResultActions actions = this.mockMvc.perform(post("/api/choice/0")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(status().isCreated());

        actions.andDo(document("{class-name}/{method-name}"));
    }

    @Test
    public void choiceUpdate() throws Exception {
        Choice choice = new Choice(USER, RESTAURANT_0, LocalDate.now());
        choiceRepository.save(choice);

        boolean limit = LocalTime.now().isAfter(TIME_LIMIT);

        ResultActions actions = this.mockMvc.perform(post("/api/choice/2")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(limit ? status().isConflict() : status().isOk());

        actions.andDo(document("{class-name}/{method-name}"));
    }

    @Test
    public void choiceIsNotFound() throws Exception {
        ResultActions actions = this.mockMvc.perform(post("/api/choice/3")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(status().isNotFound());

        actions.andDo(document("{class-name}/{method-name}"));
    }

    @Test
    public void currentChoice() throws Exception {
        Choice choice = new Choice(USER, RESTAURANT_0, LocalDate.now());
        choiceRepository.save(choice);

        ResultActions actions = this.mockMvc.perform(get("/api/choice")
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(status().isOk());

        actions.andDo(document("{class-name}/{method-name}"));
    }

}