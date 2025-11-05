package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.controllers;

import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.order.PurchasesByUserDto;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.user.DashboardResponse;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.services.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

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

    /**
     * Obtiene el reporte de compras por usuario con filtros opcionales.
     * Endpoint específico para consultas detalladas desde el dashboard.
     * Permite filtrar por rango de fechas.
     *
     * @param startDate Fecha inicial del rango (formato: yyyy-MM-dd, opcional).
     * @param endDate Fecha final del rango (formato: yyyy-MM-dd, opcional).
     * @return Lista de PurchasesByUserDto con estadísticas por usuario.
     */
    @GetMapping("/purchases/by-user")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Obtener reporte de compras por usuario",
            description = "Devuelve estadísticas detalladas de compras agrupadas por usuario. " +
                    "Permite filtrar por rango de fechas. " +
                    "Los usuarios están ordenados por monto total gastado (mayor a menor)."
    )
    public ResponseEntity<List<PurchasesByUserDto>> getPurchasesByUser(
            @Parameter(description = "Fecha inicial del rango (yyyy-MM-dd)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

            @Parameter(description = "Fecha final del rango (yyyy-MM-dd)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        log.info("Solicitud de reporte de compras por usuario - Rango: {} a {}", startDate, endDate);
        List<PurchasesByUserDto> report = dashboardService.getPurchasesByUser(startDate, endDate);
        log.info("Reporte generado: {} usuarios", report.size());
        return ResponseEntity.ok(report);
    }
}
