package com.triptacular.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.triptacular.core.Task;
import com.triptacular.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Handles REST requests for Tasks.
 * Created by Mike Atkisson on 3/15/2015.
 */
@RestController
public class TaskRestController {

    private final TaskService service;

    @Autowired
    public TaskRestController(TaskService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/tasks", method = RequestMethod.GET)
    public List<Task> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/api/tasks/task/{id}", method = RequestMethod.GET)
    public Task getById(@PathVariable("id") int id) {
        return service.getById(id);
    }

    @RequestMapping(value = "/api/tasks/task", method = RequestMethod.POST)
    public Task create(@RequestBody Task task) {
        return service.save(task);
    }

    @RequestMapping(value = "/api/tasks/task", method = RequestMethod.PUT)
    public Task update(@RequestBody Task task) {
        return service.save(task);
    }

    @RequestMapping(value = "/api/tasks/task", method = RequestMethod.DELETE)
    public void delete(@RequestBody Task task) {
        service.delete(task);
    }

    @RequestMapping(value = "/api/tasks/task/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
