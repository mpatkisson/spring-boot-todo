package com.triptacular.web;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 
 * @author Mike Atkisson 
 */
public abstract class ControllerIntegrationTest {
    
    protected MockMvc mockMvc;

    protected InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }
    
}
