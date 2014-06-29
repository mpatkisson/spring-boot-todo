package com.triptacular.web;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

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
