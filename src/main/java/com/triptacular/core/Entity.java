package com.triptacular.core;

import org.bson.types.ObjectId;

public abstract class Entity implements Comparable<Entity> {
    
    private ObjectId _id;
    
    private int id;
    
    public ObjectId getObjectId() {
        return _id;
    }
    
    public void setObjectId(ObjectId id) {
        _id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Determines if this instance is less than, equal to, or greater than 
     * another.
     * @param entity The entity to be compared.
     * @return -1 if this instance is less than the other, 0 if the two are
     *         the same, 1 if this instance is greater than the other.   
     */
    @Override
    public int compareTo(Entity entity) {
        if (entity == null) {
            throw new NullPointerException("task cannot be null");
        }
        
        int value = 0;
        if (id < entity.id) {
            value = -1;
        }
        else if (id > entity.id) {
            value = 1;
        }
        return value;
    }
}
