package com.example.health_monitoring.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("health-monitoring")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Health Monitoring API")
                        .version("1.0")
                        .description("API documentation for the Health Monitoring System")
                        .termsOfService("http://example.com/terms")
                        .contact(new Contact().name("Support Team")
                                .url("http://example.com/support")
                                .email("support@example.com"))
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
