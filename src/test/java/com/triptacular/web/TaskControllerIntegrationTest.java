package com.triptacular.web;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class TaskControllerIntegrationTest extends ControllerIntegrationTest {

    private static final String VIEW = "tasks";
    private static final String FORWARDED_URL = "/templates/tasks.html";

    MockMvc mockMvc;

    @InjectMocks
    TaskController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver resolver = viewResolver();
        mockMvc = standaloneSetup(controller)
                .setViewResolvers(resolver)
                .build();
    }
    
    @Test
    public void viewIsReturned() throws Exception {
        mockMvc.perform(get("/tasks"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW))
        .andExpect(forwardedUrl(FORWARDED_URL));
    }
    
}