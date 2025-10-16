package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.services;

import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.DashboardResponse;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.SystemMetrics;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.UserStats;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.reports.UserStatsReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servicio para gestión del dashboard administrativo.
 * Obtiene y procesa datos de diferentes servicios para generar métricas del sistema.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final RestTemplate restTemplate;

    @Value("${app.services.user-auth.url:http://localhost:8081}")
    private String userAuthServiceUrl;

    @Value("${app.services.user-auth.reports-path:/api/reports/users/active-inactive}")
    private String userStatsPath;

    /**
     * Obtiene las estadísticas de usuarios desde el servicio de autenticación.
     *
     * @return UserStatsReport con las estadísticas de usuarios.
     */
    @Cacheable(value = "userStats", unless = "#result == null")
    public UserStatsReport getUserStatsFromAuthService() {
        log.info("Obteniendo estadísticas de usuarios desde: {}{}", userAuthServiceUrl, userStatsPath);

        String url = userAuthServiceUrl + userStatsPath;

        try {
            UserStatsReport report = restTemplate.getForObject(url, UserStatsReport.class);

            if (report != null) {
                log.info("Estadísticas obtenidas exitosamente: {} activos, {} inactivos, {} total",
                        report.getActive(), report.getInactive(), report.getTotal());
            } else {
                log.warn("El servicio de autenticación retornó null");
                report = new UserStatsReport(0, 0, 0);
            }

            return report;

        } catch (HttpClientErrorException.NotFound e) {
            log.error("Endpoint no encontrado en el servicio de autenticación: {}", url);
            return new UserStatsReport(0, 0, 0);
        } catch (ResourceAccessException e) {
            log.error("Error de conexión con el servicio de autenticación: {}", e.getMessage());
            return new UserStatsReport(0, 0, 0);
        } catch (Exception e) {
            log.error("Error inesperado obteniendo estadísticas de usuarios: {}", e.getMessage());
            return new UserStatsReport(0, 0, 0);
        }
    }

    /**
     * Genera el dashboard completo con todas las métricas del sistema.
     *
     * @return DashboardResponse con todas las métricas procesadas.
     */
    @Cacheable(value = "dashboard", unless = "#result == null")
    public DashboardResponse getDashboard() {
        log.info("Generando dashboard administrativo completo");

        UserStatsReport userStatsReport = getUserStatsFromAuthService();

        // Crear estadísticas de usuarios para el dashboard
        UserStats userStats = new UserStats();
        userStats.setTotalUsers(userStatsReport.getTotal());
        userStats.setActiveUsers(userStatsReport.getActive());
        userStats.setInactiveUsers(userStatsReport.getInactive());

        // Calcular porcentaje de usuarios activos
        double activePercentage = userStatsReport.getTotal() > 0 ?
                (double) userStatsReport.getActive() / userStatsReport.getTotal() * 100 : 0;
        userStats.setActivePercentage(Math.round(activePercentage * 100.0) / 100.0);

        // Crear métricas del sistema
        SystemMetrics systemMetrics = new SystemMetrics();
        systemMetrics.setServiceStatus(checkUserServiceStatus());
        systemMetrics.setTotalRequests(calculateTotalRequests());
        systemMetrics.setUptimePercentage(calculateUptimePercentage());

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Crear y retornar el dashboard response
        DashboardResponse dashboard = new DashboardResponse();
        dashboard.setUserStats(userStats);
        dashboard.setSystemMetrics(systemMetrics);
        dashboard.setTimestamp(timestamp);

        log.info("Dashboard generado exitosamente - Timestamp: {}", timestamp);
        log.debug("Dashboard details - Total users: {}, Active: {}, Inactive: {}",
                userStats.getTotalUsers(), userStats.getActiveUsers(), userStats.getInactiveUsers());

        return dashboard;
    }

    /**
     * Verifica el estado del servicio de usuarios.
     *
     * @return String con el estado del servicio.
     */
    private String checkUserServiceStatus() {
        try {
            String healthUrl = userAuthServiceUrl + "/health";
            restTemplate.getForObject(healthUrl, String.class);
            return "OPERATIONAL";
        } catch (Exception e) {
            log.warn("Servicio de autenticación no disponible: {}", e.getMessage());
            return "DEGRADED";
        }
    }

    /**
     * Calcula el total de requests simulados para métricas del sistema.
     *
     * @return Número total de requests simulados.
     */
    private long calculateTotalRequests() {
        return 1247L;
    }

    /**
     * Calcula el porcentaje de uptime del sistema.
     *
     * @return Porcentaje de uptime simulado.
     */
    private double calculateUptimePercentage() {
        return 99.95;
    }
}
