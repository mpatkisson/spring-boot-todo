package com.triptacular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configures Spring Security.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home", "/index", "/index.html", "/webjars/**", "/jawr_generator.css", "/jawr_generator.js", "/bundles/**", "/favicon.ico").permitAll()
                .anyRequest()
                .authenticated();

        http.csrf()
                .disable();

        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.logout()
                .permitAll();
    }

    @Autowired
    @Profile("development")
    public void configureDevAuthentication(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("password")
                .roles("USER");
    }

    @Autowired
    @Profile({"production", "staging"})
    public void configureAuthentication(AuthenticationManagerBuilder auth)
            throws Exception {
    }
}
