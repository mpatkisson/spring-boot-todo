package com.triptacular;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoFactoryBean;

/**
 * A MongoFactoryBean which includes support for testing with an in memory
 * representation of MongoDB.
 * 
 * @author Mike Atkisson
 */
public class FongoFactoryBean extends MongoFactoryBean {

    private Mongo mongo;
    
    /**
     * Gets the Mongo instance created by this factory.
     * @return
     */
    @Override
    public Mongo getObject() {
        return this.mongo;
    }
    
    /**
     * Overridden to set fongo as the version of MongoDB to use.
     * @throws Exception 
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Fongo fongo = new Fongo("InMemoryMongo");
        mongo = fongo.getMongo();
    }
    
    @Override
    public void destroy() throws Exception {
        this.mongo.close();
    }
    
}
