package com.triptacular;

import com.mongodb.DB;
import com.mongodb.Mongo;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public MongoFactoryBean mongo() {
        return new TestableMongoFactoryBean();
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
