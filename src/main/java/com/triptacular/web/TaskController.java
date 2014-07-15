package com.triptacular.web;

import com.triptacular.core.Task;
import com.triptacular.services.TaskService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TaskController {

    private TaskService service;
    private HttpServletRequest request; 
    
    
    @Autowired
    public TaskController(TaskService service, HttpServletRequest request) {
        this.service = service;
        this.request = request;
    }
    
    @RequestMapping("/index.html")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");
        return view;
    }

    @RequestMapping("/api/tasks")
    public List<Task> getAll() {
        return service.getAll();
    }
    
    @RequestMapping(value = "/api/tasks/task/{id}", method = RequestMethod.GET)
    public Task getById(@PathVariable("id") int id) {
        return service.getById(id);
    }

    @RequestMapping(value = "/api/tasks/task", method = RequestMethod.POST)
    public Task create(Task task) {
        return service.save(task);
    }

    @RequestMapping(value = "/api/tasks/task", method = RequestMethod.PUT)
    public Task update(Task task) {
        return service.save(task);
    }
    
    @RequestMapping(value = "/api/tasks/task", method = RequestMethod.DELETE)
    public void delete(Task task) {
        service.delete(task);
    }
    
    @RequestMapping(value = "/api/tasks/task/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
    
}