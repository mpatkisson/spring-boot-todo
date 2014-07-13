package com.triptacular.web;

import com.triptacular.services.InMemoryTaskService;
import com.triptacular.services.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
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

    @Before
    public void setup() {
        InternalResourceViewResolver resolver = viewResolver();
        TaskService service = new InMemoryTaskService();
        TaskController controller = new TaskController(service);
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
        mockMvc.perform(get("/api/tasks"))
               .andDo(print())
               .andExpect(status().isOk());
    }
    
}