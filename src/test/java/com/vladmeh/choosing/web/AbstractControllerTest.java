package com.vladmeh.choosing.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmeh.choosing.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static com.vladmeh.choosing.utils.TestUtil.userHttpBasic;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
abstract public class AbstractControllerTest {
    static final String REST_URL = "/api";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * GET
     */
    public ResultActions testGetAll(String url, User authUser) throws Exception {
        return this.mockMvc.perform(get(url)
                .with(userHttpBasic(authUser)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public ResultActions testGetById(String url, User authUser) throws Exception {
        return this.mockMvc
                .perform(get(url)
                        .with(userHttpBasic(authUser)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public ResultActions testGetIsForbidden(String url, User authUser) throws Exception {
        return this.mockMvc
                .perform(get(url)
                        .with(userHttpBasic(authUser)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    public ResultActions testGetIsNotFound(String url, User authUser) throws Exception {
        return this.mockMvc
                .perform(get(url)
                        .with(userHttpBasic(authUser)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * CREATE
     */
    public ResultActions testCreate(String url, User authUser, String body) throws Exception {
        return mockMvc.perform(post(url)
                .with(userHttpBasic(authUser))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(body))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    public ResultActions testCreateIsConflict(String url, User authUser, String body) throws Exception {
        return mockMvc.perform(post(url)
                .with(userHttpBasic(authUser))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(body))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    public ResultActions testCreateIsForbidden(String url, User authUser, String body) throws Exception {
        return mockMvc.perform(post(url)
                .with(userHttpBasic(authUser))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(body))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    /**
     * UPDATE
     * with patch (put issue https://jira.spring.io/projects/DATAREST/issues/DATAREST-1241?filter=allopenissues)
     */
    public ResultActions testUpdate(String url, User authUser, String body) throws Exception {
        return mockMvc.perform(patch(url)
                .with(userHttpBasic(authUser))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(body))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public ResultActions testUpdateIsConflict(String url, User authUser, String body) throws Exception {
        return mockMvc.perform(patch(url)
                .with(userHttpBasic(authUser))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(body))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    public ResultActions testUpdateIsForbidden(String url, User authUser, String body) throws Exception {
        return mockMvc.perform(patch(url)
                .with(userHttpBasic(authUser))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(body))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /**
     * DELETE
     */
    public ResultActions testDelete(String url, User authUser) throws Exception {
        return mockMvc.perform(delete(url)
                .with(userHttpBasic(authUser)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    public ResultActions testDeleteIsForbidden(String url, User authUser) throws Exception {
        return mockMvc.perform(delete(url)
                .with(userHttpBasic(authUser)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    abstract public void getAll() throws Exception;

    @Test
    abstract public void getById() throws Exception;

    @Test
    abstract public void getIsNotFound() throws Exception;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    abstract public void create() throws Exception;

    @Test
    abstract public void createIsConflict() throws Exception;

    @Test
    abstract public void createIsForbidden() throws Exception;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    abstract public void update() throws Exception;

    @Test
    abstract public void updatedIsConflict() throws Exception;

    @Test
    abstract public void updateIsForbidden() throws Exception;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    abstract public void deleted() throws Exception;

    @Test
    abstract public void deletedIsForbidden() throws Exception;
}
