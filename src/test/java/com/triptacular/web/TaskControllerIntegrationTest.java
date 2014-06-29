package com.triptacular.web;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class TaskControllerIntegrationTest {

    private static final String RESPONSE_BODY = "hello";

    MockMvc mockMvc;

    @InjectMocks
    TaskController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    //@Test
    //public void thatTextReturned() throws Exception {
        //mockMvc.perform(get("/tasks/index"))
               //.andDo(print())
	       //.andExpect(content().string(RESPONSE_BODY));
    //}

}

