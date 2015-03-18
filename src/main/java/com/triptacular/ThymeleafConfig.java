package com.triptacular;

import com.github.dtrunk90.thymeleaf.jawr.JawrDialect;
import io.redbarn.RedbarnDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures Thymeleaf dialects, etc.
 */
@Configuration
public class ThymeleafConfig {
    
    @Bean
    public JawrDialect jawrDialect() {
        return new JawrDialect();
    }

    @Bean
    public RedbarnDialect redbarnDialect() {
        return new RedbarnDialect();
    }
}
