package com.triptacular.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TaskController {

    @RequestMapping("/tasks")
    String index() {
        return "tasks";
    }

    // GET all tasks
    // @RequestMapping("/tasks")

    // GET task by ID
    // @RequestMapping("/tasks/task/{id}")

    // POST a new task
    // @RequestMapping("/tasks/task")

    // PUT an updated task
    // @RequestMapping("/tasks/task")

    // DELETE a task by ID
    // @RequestMapping("/tasks/task/{id}")
}
