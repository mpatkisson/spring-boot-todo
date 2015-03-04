package com.triptacular;

import com.mongodb.DB;
import com.mongodb.Mongo;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Configures MongoDB for development.
 * 
 * @author Mike Atkisson
 */
@Configuration
@Profile("development")
public class FongoConfig extends MongoConfig {

    @Override
    @Bean
    public MongoFactoryBean mongo() {
        return new FongoFactoryBean();
    }

}
