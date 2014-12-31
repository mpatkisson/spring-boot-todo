package com.triptacular.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles authentication related requests.
 */
@RestController
public class AuthController {

    @RequestMapping(value = {"/login", "/login.html"})
    public ModelAndView login() {
        return new ModelAndView("login");
    }

}
