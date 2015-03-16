package com.triptacular.services;

import com.triptacular.core.Entity;

import java.util.List;

/**
 * Performs CRUD operations for an entity.
 * @author Mike Atkisson
 */
public interface EntityService<TEntity extends Entity> {

    /**
     * Gets all entities.
     * @return A list of tasks.
     */
    List<TEntity> getAll();

    /**
     * Gets an entity by ID.
     * @param id The ID of the entity.
     * @return A entity or null if the entity was not found.
     */
    TEntity getById(int id);
    
    /**
     * Gets the total number of saved entities.
     * @return The number of saved entities.
     */
    int getCount();

    /**
     * Gets a list of entities ordered by ID.
     * @return A list of entities ordered by ID.
     */
    List<TEntity> getSorted();

    /**
     * Saves a new or existing entities.
     * @param entity The entity to add or update.
     * @return The newly added or updated entity.
     */
    TEntity save(TEntity entity);
    
    /**
     * Adds a new entity.
     * @param entity The entity to add.
     * @return The newly added entity.
     */
    TEntity add(TEntity entity);
    
    /**
     * Updates an existing TEntiy entity
     * @param entity The entity to update.
     * @return The updated entity.
     */
    TEntity update(TEntity entity);
    
    /**
     * Deletes an entity.
     * @param entity The entity to delete.
     */
    void delete(TEntity entity);
}
