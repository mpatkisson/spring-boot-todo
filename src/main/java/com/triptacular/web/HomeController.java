package com.triptacular.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * Handles requests for the main home page.
 */
@RestController
public class HomeController {

    @RequestMapping(value = {"/", "/home", "/index", "/index.html"})
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
