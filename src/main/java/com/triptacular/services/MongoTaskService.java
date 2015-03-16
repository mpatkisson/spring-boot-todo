package com.triptacular.services;

import com.google.common.collect.Lists;
import com.triptacular.core.Task;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides MongoDB CRUD related services for Task typed entities.
 * @author Mike Atkisson
 */
@Component
public class MongoTaskService implements TaskService {

    private final MongoCollection tasks;
    
    @Autowired
    public MongoTaskService(MongoCollection tasks) {
        this.tasks = tasks;
    }
    
    @Override
    public Task add(String item) {
        Task task = new Task();
        task.setItem(item);
        int id = getNextId();
        task.setId(id);
        tasks.insert(task);
        return task;
    }

    @Override
    public void delete(int id) {
        tasks.remove("{ id: # }", id);
    }

    @Override
    public List<Task> getAll() {
        List<Task> all = new ArrayList();
        Iterable<Task> found = tasks.find().as(Task.class);
        if (found != null) {
            all = Lists.newArrayList(found);
        }
        return all;
    }

    @Override
    public Task getById(int id) {
        return tasks.findOne("{ id: # }", id).as(Task.class);
    }

    @Override
    public int getCount() {
        return (int)tasks.count();
    }

    @Override
    public List<Task> getSorted() {
        List<Task> sorted = new ArrayList();
        Iterable<Task> found = tasks.find()
                                    .sort("{ id: 1 }")
                                    .as(Task.class);
        try {
            sorted = Lists.newArrayList(found);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return sorted;
    }

    @Override
    public Task save(Task entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        
        int id = entity.getId();
        Task existing = getById(id);
        if (existing == null) {
            entity = add(entity);
        }
        else {
            entity = update(entity);
        }
        return entity;
    }

    @Override
    public Task add(Task entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        
        int id = getNextId();
        entity.setId(id);
        tasks.insert(entity);
        return entity;
    }

    @Override
    public Task update(Task entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        
        tasks.update("{ id: # }", entity.getId()).with(entity);
        return entity;
    }

    @Override
    public void delete(Task entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        
        int id = entity.getId();
        delete(id);
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
