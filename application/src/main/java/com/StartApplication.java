package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com")
//Note that, To enable JPA Auditing, you need to add the @EnableJpaAuditing annotation to one of the configuration classes
@EnableJpaAuditing
@EntityScan(basePackages = {"com.library.models"})  // scan JPA entities
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
