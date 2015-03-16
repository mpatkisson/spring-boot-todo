package com.triptacular.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triptacular.Application;
import com.triptacular.services.TaskService;
import org.jongo.MongoCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration("file:src/main/resources")
@IntegrationTest
public class TaskControllerIntegrationTest extends ControllerIntegrationTest {

    private static final String VIEW = "tasks";
    private static final String FORWARDED_URL = "/templates/tasks.html";
    private static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    private ObjectMapper mapper;
    
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private TaskController controller;
    
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
    public void viewIsReturned() throws Exception {
        mockMvc.perform(get("/tasks.html"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW))
        .andExpect(forwardedUrl(FORWARDED_URL));
    }
    
}
