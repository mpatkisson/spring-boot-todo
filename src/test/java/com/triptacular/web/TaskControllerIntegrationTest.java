package com.triptacular.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.triptacular.Application;
import com.triptacular.core.Task;
import com.triptacular.services.InMemoryTaskService;
import com.triptacular.services.TaskService;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TaskControllerIntegrationTest extends ControllerIntegrationTest {

    private static final String VIEW = "index";
    private static final String FORWARDED_URL = "/templates/index.html";
    private static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    private ObjectMapper mapper;

    @Autowired
    private Mongo mongo;
    
    @Autowired
    private TaskController controller;
    
    @Autowired
    private TaskService service;

    @Before
    public void setup() {
        InternalResourceViewResolver resolver = viewResolver();
        mapper = new ObjectMapper();
        mockMvc = standaloneSetup(controller).setViewResolvers(resolver).build();
    }

    @After
    public void tearDown() {
        DB db = mongo.getDB("todo");
        Jongo jongo = new Jongo(db);
        MongoCollection collection = jongo.getCollection("tasks");
        collection.remove();
    }
    
    @Test
    public void viewIsReturned() throws Exception {
        mockMvc.perform(get("/index.html"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW))
        .andExpect(forwardedUrl(FORWARDED_URL));
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
