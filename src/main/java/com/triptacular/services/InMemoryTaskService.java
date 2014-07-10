package com.triptacular.services;

import com.triptacular.core.Task;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Provides an in memory implementation of TaskService
 * @author Mike Atkisson
 */
@Component
public class InMemoryTaskService implements TaskService {
    private List<Task> tasks;
    
    public InMemoryTaskService() {
        tasks = new ArrayList<Task>();
    }

    @Override
    public List<Task> getAll() {
        return tasks;
    }
    
    @Override
    public Task getById(int id) {
        Task task = null;
        for(Task t : tasks) {
            if (t.getId() == id) {
                task = t;
                break;
            }
        }
        return task;
    }
    
    public Task getFirst() {
        Task first = null;
        List<Task> ordered = getSorted();
        if (ordered.size() > 0) {
            first = ordered.get(0);
        }
        return first;
    }
    
    @Override
    public int getCount() {
        return tasks.size();
    }
    
    @Override
    public Task add(Task task) {
        int id = getNextId();
        task.setId(id);
        tasks.add(task);
        return task;
    }
    
    @Override
    public Task update(Task task) {
        Task original = getById(task.getId());
        if (original != null) {
            String item = task.getItem();
            original.setItem(item);
        }
        return task;
    }
    
    @Override
    public Task save(Task task) {
        if (task == null) {
            throw new NullPointerException();
        }
        
        int id = task.getId();
        Task existing = getById(id);
        if (existing == null) {
            task = add(task);
        }
        else {
            task = update(task);
        }
        return task;
    }
    
    public void delete(int id) {
        Task task = getById(id);
        if (task != null) {
            tasks.remove(task);
        }
    }
    
    @Override
    public void delete(Task task) {
        if (task == null) {
            throw new NullPointerException();
        }
        
        delete(task.getId());
    }
    
    @Override
    public List<Task> getSorted() {
        List<Task> ordered = new ArrayList(tasks);
        Collections.sort(ordered);
        return ordered;
    }
    
    public int getLastId() {
        int id = 0;
        List<Task> ordered = getSorted();
        if (ordered.size() > 0) {
            int index = ordered.size() - 1;
            Task last = ordered.get(index);
            id = last.getId();
        }
        return id;
    }
    
    public int getNextId() {
        int last = getLastId();
        return last + 1;
    }
    
}