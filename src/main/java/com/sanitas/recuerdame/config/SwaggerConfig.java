package com.sanitas.recuerdame.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI recuerdameOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("RecuerdaMe API")
                        .description("REST API built with Java/Spring Boot that allows users to register medications and schedule reminders for their intakes. " +
                                "It implements layered architecture with JPA/Hibernate, and validation rules to prevent duplicate records.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("RecuerdaMe Team")
                                .email("support@recuerdame.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT"))
                );
    }
}
