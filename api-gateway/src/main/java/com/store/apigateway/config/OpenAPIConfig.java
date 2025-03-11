package com.store.apigateway.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class OpenAPIConfig {

    @Bean
    public OpenAPI gatewayServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("Gateway Service API")
                        .description("Documentation for Gateway Service API")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")));
    }
}
