package com.org.jwtoauth;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SecurityScheme(name = "bearer-key", type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT", scheme = "bearer")
@SpringBootApplication
public class JWTOAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(JWTOAuthApplication.class, args);
    }

}
