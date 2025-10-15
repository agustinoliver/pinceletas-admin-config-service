package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase principal de la aplicación Spring Boot para el servicio de administración y configuración de Pinceletas.
 * Sirve como punto de entrada para iniciar el servicio completo de dashboards y reportes administrativos.
 * Configura la aplicación con Spring Boot, habilita tareas programadas y caching.
 */
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class PinceletasAdminConfigServiceApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 * Configura y lanza el servidor embebido con toda la configuración de seguridad y servicios.
	 * Inicializa todos los beans, configuraciones y servicios definidos en la aplicación.
	 *
	 * @param args Argumentos de línea de comandos para configuración adicional de Spring Boot.
	 */
	public static void main(String[] args) {
		SpringApplication.run(PinceletasAdminConfigServiceApplication.class, args);
	}

}
