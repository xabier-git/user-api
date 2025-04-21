package cl.sentra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30) // OpenAPI 3.0
                .select()
                .apis(RequestHandlerSelectors.basePackage("cl.sentra.controller")) // Paquete base de los controladores
                .paths(PathSelectors.any()) // Documentar todos los endpoints
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("API RESTful de creación de usuarios.")
                        .description("Documentación de la API para la gestión de usuarios")
                        .version("1.0.0")
                        .contact(new Contact("Javier Aguirre Araya", "https://github.com/xabier-git", "javier.aguirre.araya@gmail.com"))
                        .build());
    }
}