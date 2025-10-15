package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración de componentes generales de la aplicación.
 */
@Configuration
public class AppConfig {

    /**
     * Crea un RestTemplate para realizar peticiones HTTP a otros servicios.
     *
     * @return RestTemplate configurado.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
