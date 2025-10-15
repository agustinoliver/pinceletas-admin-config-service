package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para documentación de la API.
 * Define la información general de la API y el esquema de seguridad JWT.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configura la documentación OpenAPI personalizada de la aplicación.
     * Incluye información del servicio administrativo.
     *
     * @return OpenAPI configurada con información del servicio.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pinceletas Admin Config Service API")
                        .version("1.0")
                        .description("API para dashboard administrativo y configuración de Pinceletas - Tesis")
                        .contact(new Contact()
                                .name("Equipo Pinceletas - Tesis")
                                .email("support@pinceletas.com")));
    }
}
