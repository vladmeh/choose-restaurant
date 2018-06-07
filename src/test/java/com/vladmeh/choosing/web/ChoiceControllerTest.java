package com.vladmeh.choosing.web;

import com.vladmeh.choosing.model.Choice;
import com.vladmeh.choosing.repository.ChoiceRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.vladmeh.choosing.RestaurantTestData.RESTAURANT_0;
import static com.vladmeh.choosing.TestUtil.userAuth;
import static com.vladmeh.choosing.UserTestData.USER;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

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

        /*this.mockMvc.perform(get("/api/choice")
                .with(userAuth(USER)))
                .andDo(print())
                //.andDo(new TestResultHandler())
                .andDo(document("{class-name}/{method-name}"))
                .andExpect(status().isNotFound());*/
    }

    @Test
    public void choiceCreate() throws Exception {
        this.mockMvc.perform(post("/api/choice/0")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER)))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void choiceUpdate() throws Exception {
        Choice choice = new Choice(USER, RESTAURANT_0, LocalDate.now());
        choiceRepository.save(choice);

        boolean limit = LocalTime.now().isAfter(TIME_LIMIT);

        this.mockMvc.perform(post("/api/choice/2")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER)))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"))
                .andExpect(limit ? status().isConflict() : status().isOk());
    }

    @Test
    public void choiceIsNotFound() throws Exception {
        this.mockMvc.perform(post("/api/choice/3")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER)))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void currentChoice() throws Exception {
        Choice choice = new Choice(USER, RESTAURANT_0, LocalDate.now());
        choiceRepository.save(choice);

        this.mockMvc.perform(get("/api/choice")
                .with(userAuth(USER)))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"))
                .andExpect(status().isOk());
    }

}