package com.triptacular.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triptacular.core.Task;
import com.triptacular.services.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles task related requests.
 */
@Controller
public class TaskController {

    private final TaskService service;
    
    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }
    
    @RequestMapping(value = {"/tasks", "/tasks.html"})
    public ModelAndView index() throws JsonProcessingException {
        ModelAndView view = new ModelAndView("tasks");
        List<Task> tasks = service.getAll();
        view.addObject("tasks", tasks);
        return view;
    }

    @RequestMapping(value = {"/tasks/create", "/tasks.html/create"}, method = RequestMethod.POST )
    public String createTask(Task task)
            throws JsonProcessingException {
        service.save(task);
        return "redirect:/tasks";
    }
    
}
