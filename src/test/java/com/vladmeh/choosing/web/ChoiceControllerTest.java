package com.vladmeh.choosing.web;

import com.vladmeh.choosing.repository.ChoiceRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static com.vladmeh.choosing.TestUtil.userAuth;
import static com.vladmeh.choosing.UserTestData.USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 06.06.2018.
 * @link https://github.com/vladmeh/choosing-topjava
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// @TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
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
    public void current() throws Exception {
        this.mockMvc.perform(get("/api/choice")
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void choice() throws Exception {
        this.mockMvc.perform(post("/api/choice/0")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(status().isCreated());

        boolean limit = LocalTime.now().isAfter(TIME_LIMIT);

        this.mockMvc.perform(post("/api/choice/2")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(limit ? status().isConflict() : status().isOk());
    }
}