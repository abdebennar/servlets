package org.cinema;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Database database() {
        System.out.println("🔵 Creating Database bean!");
        Database db = new Database();
        return db;
    }
}
