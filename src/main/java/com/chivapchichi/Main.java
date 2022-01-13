package com.chivapchichi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "My application API", version = "1.0", description = "My db web services"))
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
