package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.controllers;

import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.common.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para verificar el estado de salud del servicio administrativo.
 */
@RestController
public class HealthController {

    /**
     * Verifica que la aplicación esté en funcionamiento.
     *
     * @return MessageResponse indicando que la aplicación está activa.
     */
    @GetMapping("/health")
    public ResponseEntity<MessageResponse> health() {
        return ResponseEntity.ok(MessageResponse.of("Admin Config Service is running!"));
    }
}
