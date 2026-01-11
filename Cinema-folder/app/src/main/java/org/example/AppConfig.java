package org.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.util.Random;

@Configuration
public class AppConfig {

    @Bean
    public Integer randomNumberA() {
        int number = new Random().nextInt(1000);
        System.out.println("Bean A created with number: " + number);
        return number;
    }

    @Bean
    public Integer randomNumberB() {
        int number = new Random().nextInt(1000);
        System.out.println("Bean B created with number: " + number);
        return number;
    }
}
