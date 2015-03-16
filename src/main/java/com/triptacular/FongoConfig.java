package com.triptacular;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoFactoryBean;

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
