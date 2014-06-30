package com.triptacular.web;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 
 * @author Mike Atkisson 
 */
public abstract class ControllerIntegrationTest {
    
    protected InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }
    
}
