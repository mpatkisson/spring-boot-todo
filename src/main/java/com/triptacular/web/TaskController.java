package com.triptacular.web;

import com.triptacular.core.Task;
import com.triptacular.services.InMemoryTaskService;
import com.triptacular.services.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TaskController {

    @Autowired
    private TaskService service;
    
    @RequestMapping("/index.html")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");
        return view;
    }

    @RequestMapping("/tasks")
    public List<Task> getAll() {
        return service.getAll();
    }
    
    /*

    @RequestMapping("/tasks/task/{id}")
    public Task getById(int id) {
        return service.getById(id);
    }
    
    @RequestMapping(value = "/tasks/task", method = RequestMethod.POST)
    public Task create(Task task) {
        return service.save(task);
    }
    
    @RequestMapping(value = "/tasks/task", method = RequestMethod.PUT)
    public Task update(Task task) {
        return service.save(task);
    }
    
    @RequestMapping(value = "/tasks/task", method = RequestMethod.DELETE)
    public void delete(Task task) {
        service.delete(task);
    }
    
    */
    
}