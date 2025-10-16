package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.controllers;

import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.DashboardResponse;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.services.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para el dashboard administrativo.
 * Proporciona endpoints para obtener métricas y estadísticas del sistema.
 */
@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Dashboard", description = "API para dashboard administrativo con métricas del sistema")
@SecurityRequirement(name = "Bearer Authentication")
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * Obtiene todas las métricas del dashboard administrativo.
     * Incluye estadísticas de usuarios y métricas del sistema.
     *
     * @return DashboardResponse con todas las métricas procesadas.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Obtener dashboard completo",
            description = "Devuelve todas las métricas del sistema para el dashboard administrativo"
    )
    public ResponseEntity<DashboardResponse> getDashboard() {
        log.info("Solicitud de dashboard completo recibida");
        DashboardResponse dashboard = dashboardService.getDashboard();
        return ResponseEntity.ok(dashboard);
    }

    /**
     * Obtiene solo las estadísticas de usuarios activos/inactivos.
     * Endpoint específico para gráficos de usuarios.
     *
     * @return DashboardResponse con solo las estadísticas de usuarios.
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Obtener estadísticas de usuarios",
            description = "Devuelve conteos de usuarios activos e inactivos para gráficos"
    )
    public ResponseEntity<DashboardResponse> getUserStats() {
        log.info("Solicitud de estadísticas de usuarios recibida");
        DashboardResponse dashboard = dashboardService.getDashboard();
        return ResponseEntity.ok(dashboard);
    }
}
