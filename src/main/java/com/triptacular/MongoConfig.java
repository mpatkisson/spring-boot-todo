package com.triptacular;

import com.mongodb.DB;
import com.mongodb.Mongo;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Configures MongoDB for IOC, etc.
 * 
 * @author Mike Atkisson
 */
@Configuration
//@ComponentScan
public class MongoConfig {
    
    @Bean
    public MongoFactoryBean mongo() {
        return new TriptacularMongoFactoryBean();
    }
    
    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        Mongo mongo = mongo().getObject();
        return new SimpleMongoDbFactory(mongo, "todo");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
    
    @Bean
    public DB database() throws Exception {
        return mongoDbFactory().getDb();
    }
    
    @Bean
    public Jongo jongo() throws Exception {
        DB db = database();
        return new Jongo(db);
    }
    
    @Bean
    public MongoCollection tasks() throws Exception {
        Jongo jongo = jongo();
        return jongo.getCollection("tasks");
    }
    
}
