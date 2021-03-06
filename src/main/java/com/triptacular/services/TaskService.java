package com.triptacular.services;

import com.triptacular.core.Task;

/**
 *
 * @author mike
 */
public interface TaskService extends EntityService<Task> {
    
    /**
     * Adds a new task.
     * @param item The todo item associated with the task.
     * @return The newly added Task.
     */
    Task add(String item); 
    
    /**
     * Deletes an existing task.
     * @param id The ID of the task to delete.
     */
    void delete(int id);
}
