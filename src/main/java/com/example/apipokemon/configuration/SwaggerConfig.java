package com.example.apipokemon.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {



  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.apipokemon.rest"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(metaData());
  }

  private ApiInfo metaData() {
    return new ApiInfo(
            "API Pokémon",
            "Spring Boot REST API pour gérer la liste du Pokémon",
            "1.0",
            "Terms of service",
            new Contact("Francisco Javier Moreno", "https://www.linkedin.com/in/javiermoreno1986/", "javiermoreno1986@gmail.com"),
            "Apache License Version 2.0",
            "https://www.apache.org/licenses/LICENSE-2.0",new ArrayList<VendorExtension>());
  }




}
