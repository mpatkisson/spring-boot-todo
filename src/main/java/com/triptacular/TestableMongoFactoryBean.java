package com.triptacular;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.data.mongodb.core.MongoFactoryBean;

/**
 * A MongoFactoryBean which includes support for testing with an in memory
 * representation of MongoDB.
 * 
 * @author Mike Atkisson
 */
public class TestableMongoFactoryBean extends MongoFactoryBean {
    
    private Mongo mongo;
    private String env = "test";
    
    /**
     * Gets the Mongo instance created by this factory.
     * @return
     */
    @Override
    public Mongo getObject() {
        return this.mongo;
    }
    
    /**
     * Sets the private
     * @throws Exception 
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (env.equals("test")) {
            Fongo fongo = new Fongo("InMemoryMongo");
            this.mongo = fongo.getMongo();
        } else {
            super.afterPropertiesSet();
            this.mongo = super.getObject();
        }
    }
    
}
