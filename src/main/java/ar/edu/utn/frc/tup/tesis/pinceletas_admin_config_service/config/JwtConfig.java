package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.config;

import ar.edu.utn.frc.tup.tesis.pinceletas.common.security.JwtAuthenticationFilter;
import ar.edu.utn.frc.tup.tesis.pinceletas.common.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService) {
        return new JwtAuthenticationFilter(jwtService);
    }
}
