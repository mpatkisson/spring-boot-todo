package com.triptacular;

import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * Configures MongoDB for development.
 * 
 * @author Mike Atkisson
 */
@Configuration
@Profile("development")
public class FongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "todo";
    }

    @Override
    public MongoClient mongo() throws Exception {
        return new Fongo("in-memory-db").getMongo();
    }

    @Bean
    public Jongo jongo() throws Exception {
        DB db = mongoDbFactory().getDb();
        //DB db = mongo().getDB(getDatabaseName());
        return new Jongo(db);
    }

    @Bean
    public MongoCollection tasks() throws Exception {
        Jongo jongo = jongo();
        return jongo.getCollection("tasks");
    }

}
