package com.triptacular;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Configures MongoDB for IOC, etc.
 * 
 * @author Mike Atkisson
 */
@Configuration
@Profile({"production", "staging"})
public class MongoConfig {

    @Bean
    public MongoClientFactoryBean mongo() {
        return new MongoClientFactoryBean();
    }
    
    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(), "todo");
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

    @Bean
    public MongoCollection users() throws Exception {
        Jongo jongo = jongo();
        return jongo.getCollection("users");
    }
    
}
