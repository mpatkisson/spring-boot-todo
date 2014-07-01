package com.triptacular.web;

import com.triptacular.core.Task;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class TaskController {

    private List<Task> tasks;
    
    @RequestMapping("/tasks/index")
    public String index() {
        return "tasks";
    }

    @RequestMapping("/tasks")
    public List<Task> getAll() {
        return tasks;
    }

    // GET task by ID
    // @RequestMapping("/tasks/task/{id}")

    // POST a new task
    // @RequestMapping("/tasks/task")

    // PUT an updated task
    // @RequestMapping("/tasks/task")

    // DELETE a task by ID
    // @RequestMapping("/tasks/task/{id}")
}
