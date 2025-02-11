package ru.alex.burdovitsin.mesh.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Mesh Group Example Aplication",
                description = "Тестовое приложение для Mesh Group",
                version = "0.0.1",
                contact = @Contact(
                        name = "Burdovitsin Aleksandr",
                        email = "alex4rf@gmail.com"
                )
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecuritySchemes({
        @SecurityScheme(name = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
public class OpenApiConfig {
}
