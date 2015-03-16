package com.triptacular.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triptacular.Application;
import com.triptacular.core.Task;
import com.triptacular.services.TaskService;
import com.triptacular.web.ControllerIntegrationTest;
import org.jongo.MongoCollection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletContext;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration("file:src/main/resources")
@IntegrationTest
public class TaskRestControllerIntegrationTest extends ControllerIntegrationTest {

    private static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    private ObjectMapper mapper;
    
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private TaskRestController controller;
    
    @Autowired
    private TaskService service;
    
    @Autowired
    private MongoCollection tasks;

    @Before
    public void setup() {
        InternalResourceViewResolver resolver = viewResolver();
        mapper = new ObjectMapper();
        mockMvc = standaloneSetup(controller).setViewResolvers(resolver).build();
    }

    @After
    public void tearDown() {
        tasks.remove();
    }

    @Test
    public void canGetAll() throws Exception {
        service.add("Do this");
        service.add("Do that");
        mockMvc.perform(get("/api/tasks"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(JSON_CONTENT_TYPE))
               .andExpect(jsonPath("$", hasSize(2)));
    }
    
    @Test
    public void canGetById() throws Exception {
        Task added = service.add("Do this");
        int id = added.getId();
        String url = "/api/tasks/task/" + id;
        mockMvc.perform(get(url))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(JSON_CONTENT_TYPE))
               .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    public void canCreate() throws Exception {
        Task task = new Task();
        task.setItem("Do This");
        String json = mapper.writeValueAsString(task);
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/api/tasks/task")
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(json);
        mockMvc.perform(request)
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(JSON_CONTENT_TYPE))
               .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void canUpdate() throws Exception {
        Task task = service.add("Do this");
        int id = task.getId();
        String item = "Do that";
        task.setItem(item);
        String json = mapper.writeValueAsString(task);
        MockHttpServletRequestBuilder request;
        request = MockMvcRequestBuilders.put("/api/tasks/task")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(json);
        mockMvc.perform(request)
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(JSON_CONTENT_TYPE))
               .andExpect(jsonPath("$.item").value(item));
    }

    @Test
    public void canDeleteUsingTaskInstance() throws Exception {
        service.add("Do this");
        List<Task> tasks = service.getAll();
        Assert.assertTrue(tasks.size() > 0);
        Task task = tasks.get(0);
        String json = mapper.writeValueAsString(task);
        MockHttpServletRequestBuilder request;
        request = MockMvcRequestBuilders.delete("/api/tasks/task")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(json);
        mockMvc.perform(request)
               .andDo(print())
               .andExpect(status().isOk());
        Assert.assertTrue(service.getCount() == 0);
    }

    @Test
    public void canDeleteUsingTaskId() throws Exception {
        service.add("Do this");
        List<Task> tasks = service.getAll();
        Assert.assertTrue(tasks.size() > 0);
        Task task = tasks.get(0);
        int id = task.getId();
        MockHttpServletRequestBuilder request;
        request = MockMvcRequestBuilders.delete("/api/tasks/task/" + id);
        mockMvc.perform(request)
               .andDo(print())
               .andExpect(status().isOk());
        Assert.assertTrue(service.getCount() == 0);
    }
    
}
