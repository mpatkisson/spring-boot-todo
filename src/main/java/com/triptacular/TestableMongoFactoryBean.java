package com.triptacular;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.data.mongodb.core.MongoFactoryBean;

/**
 * A MongoFactoryBean which includes support for testing with an in memory
 * representation of MongoDB.
 * 
 * @author Mike Atkisson
 */
public class TestableMongoFactoryBean extends MongoFactoryBean {
    
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
     * Sets the private
     * @throws Exception 
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Settings settings = new Settings();
        if (settings.getDbType().equals("fongo")) {
            Fongo fongo = new Fongo("InMemoryMongo");
            mongo = fongo.getMongo();
        } else {
            super.afterPropertiesSet();
            mongo = super.getObject();
        }
    }
    
    @Override
    public void destroy() throws Exception {
        this.mongo.close();
    }
    
}
