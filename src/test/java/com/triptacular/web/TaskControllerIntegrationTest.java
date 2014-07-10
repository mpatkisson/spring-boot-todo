package com.triptacular.web;

import com.triptacular.core.Task;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class TaskControllerIntegrationTest extends ControllerIntegrationTest {

    private static final String VIEW = "index";
    private static final String FORWARDED_URL = "/templates/index.html";

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
        mockMvc.perform(get("/index.html"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW))
        .andExpect(forwardedUrl(FORWARDED_URL));
    }
    
    //@Test
    public void canGetAll() throws Exception {
        mockMvc.perform(get("/tasks"))
               .andExpect(status().isOk());
    }
    
}