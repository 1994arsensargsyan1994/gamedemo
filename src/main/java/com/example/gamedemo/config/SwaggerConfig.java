package com.example.gamedemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(){
              return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.example.gamedemo.controller"))
            .paths(PathSelectors.any()).build().pathMapping("/")
        .apiInfo(apiInfo());
}

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Example Game Demo Api.",
                "Rest Endpoints for Example Game Demo API",
                "1.0.0",
                "Terms of service",
                new Contact("Example Game Demo", "www.example.gamedemo.com", "1994arsensargsyan@gmail.com"),
                "License of Example Game Demo Api.", "API license URL", Collections.emptyList());
    }
}

