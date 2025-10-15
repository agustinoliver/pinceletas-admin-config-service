package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad de Spring Security.
 * Define las reglas de autorización y autenticación básica para el servicio administrativo.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad y las reglas de autorización.
     * Define qué endpoints son públicos y cuáles requieren autenticación.
     *
     * @param http Objeto HttpSecurity para configurar la seguridad.
     * @return SecurityFilterChain configurada.
     * @throws Exception Si hay error en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/health").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**",
                                "/webjars/**", "/api-docs/**", "/swagger-ui.html").permitAll()

                        // Endpoints de dashboard - requieren autenticación y rol ADMIN
                        .requestMatchers("/api/admin/dashboard/**").hasRole("ADMIN")

                        // Todos los demás endpoints requieren autenticación
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // Usar autenticación básica
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                );

        return http.build();
    }

    /**
     * Configura usuarios en memoria para el servicio administrativo.
     * En producción, esto podría conectarse a una base de datos o servicio de autenticación.
     *
     * @return UserDetailsService con usuarios administrativos.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails monitor = User.builder()
                .username("monitor")
                .password(passwordEncoder().encode("monitor123"))
                .roles("MONITOR")
                .build();

        return new InMemoryUserDetailsManager(admin, monitor);
    }

    /**
     * Crea el codificador de contraseñas usando BCrypt.
     *
     * @return PasswordEncoder configurado con BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}