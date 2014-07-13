package com.triptacular.web;

import com.triptacular.core.Task;
import com.triptacular.services.InMemoryTaskService;
import com.triptacular.services.TaskService;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Before;
import org.junit.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class TaskControllerIntegrationTest extends ControllerIntegrationTest {

    private static final String VIEW = "index";
    private static final String FORWARDED_URL = "/templates/index.html";
    private TaskController controller;
    private TaskService service;

    @Before
    public void setup() {
        InternalResourceViewResolver resolver = viewResolver();
        service = new InMemoryTaskService();
        controller = new TaskController(service);
        mockMvc = standaloneSetup(controller)
                .setViewResolvers(resolver)
                .build();
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
               .andExpect(content().contentType("application/json;charset=UTF-8"))
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
               .andExpect(content().contentType("application/json;charset=UTF-8"))
               .andExpect(jsonPath("$.id").value(id));
    }
    
}