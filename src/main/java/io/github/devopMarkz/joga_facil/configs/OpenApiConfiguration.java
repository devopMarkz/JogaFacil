package io.github.devopMarkz.joga_facil.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API - Organização de Partidas de Futebol.",
                summary = "API feita com o intuito de organizar as partidas de futebol entre o CEST e Grupo Atlântica.",
                version = "v1",
                contact = @Contact(
                        name = "Marcos André Costa da Silva",
                        email = "marcosdev2002@gmail.com"
                )
        )
)
public class OpenApiConfiguration {
}
