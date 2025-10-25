package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.services;


import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.product.ProductStatsDto;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.product.ProductsByCategoryDto;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.product.TopSellingProductDto;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.user.DashboardResponse;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.user.SystemMetrics;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.user.UserStats;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.reports.UserStatsReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Value("${app.services.commerce.url:http://localhost:8080}")
    private String commerceServiceUrl;

    @Cacheable(value = "userStats", unless = "#result == null")
    public UserStatsReport getUserStatsFromAuthService() {
        log.info("Obteniendo estadísticas de usuarios desde: {}{}", userAuthServiceUrl, userStatsPath);
        String url = userAuthServiceUrl + userStatsPath;

        try {
            UserStatsReport report = restTemplate.getForObject(url, UserStatsReport.class);
            if (report != null) {
                log.info("Estadísticas de usuarios obtenidas: {} activos, {} inactivos",
                        report.getActive(), report.getInactive());
            } else {
                log.warn("El servicio de autenticación retornó null");
                report = new UserStatsReport(0, 0, 0);
            }
            return report;
        } catch (Exception e) {
            log.error("Error obteniendo estadísticas de usuarios: {}", e.getMessage());
            return new UserStatsReport(0, 0, 0);
        }
    }

    @Cacheable(value = "productGeneralStats", unless = "#result == null")
    public ProductStatsDto getProductGeneralStats() {
        log.info("Obteniendo estadísticas generales de productos");
        String url = commerceServiceUrl + "/api/reports/products/general-stats";

        try {
            ResponseEntity<Map<String, Long>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Long>>() {}
            );

            if (response.getBody() != null) {
                Map<String, Long> stats = response.getBody();
                ProductStatsDto dto = new ProductStatsDto();
                dto.setTotalProducts(stats.getOrDefault("totalProducts", 0L));
                dto.setActiveProducts(stats.getOrDefault("activeProducts", 0L));
                dto.setInactiveProducts(stats.getOrDefault("inactiveProducts", 0L));

                log.info("Estadísticas de productos obtenidas: {} total, {} activos",
                        dto.getTotalProducts(), dto.getActiveProducts());
                return dto;
            }
        } catch (Exception e) {
            log.error("Error obteniendo estadísticas de productos: {}", e.getMessage());
        }
        return new ProductStatsDto(0L, 0L, 0L);
    }

    @Cacheable(value = "productsByCategory", unless = "#result.isEmpty()")
    public List<ProductsByCategoryDto> getProductsByCategory() {
        log.info("Obteniendo productos por categoría");
        String url = commerceServiceUrl + "/api/reports/products/by-category";

        try {
            ResponseEntity<List<ProductsByCategoryDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ProductsByCategoryDto>>() {}
            );

            if (response.getBody() != null) {
                List<ProductsByCategoryDto> products = response.getBody();
                log.info("Productos por categoría obtenidos: {} categorías", products.size());
                return products;
            }
        } catch (Exception e) {
            log.error("Error obteniendo productos por categoría: {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    @Cacheable(value = "topSellingProducts", unless = "#result.isEmpty()")
    public List<TopSellingProductDto> getTopSellingProducts(Integer limit) {
        log.info("Obteniendo top {} productos más vendidos", limit);
        String url = commerceServiceUrl + "/api/reports/products/top-selling?limit=" + limit;

        try {
            ResponseEntity<List<TopSellingProductDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TopSellingProductDto>>() {}
            );

            if (response.getBody() != null) {
                List<TopSellingProductDto> products = response.getBody();
                log.info("Top productos más vendidos obtenidos: {} productos", products.size());
                return products;
            }
        } catch (Exception e) {
            log.error("Error obteniendo productos más vendidos: {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    @Cacheable(value = "dashboard", unless = "#result == null")
    public DashboardResponse getDashboard() {
        log.info("Generando dashboard administrativo completo");

        // Obtener datos de usuarios
        UserStatsReport userStatsReport = getUserStatsFromAuthService();
        UserStats userStats = new UserStats();
        userStats.setTotalUsers(userStatsReport.getTotal());
        userStats.setActiveUsers(userStatsReport.getActive());
        userStats.setInactiveUsers(userStatsReport.getInactive());

        double activePercentage = userStatsReport.getTotal() > 0 ?
                (double) userStatsReport.getActive() / userStatsReport.getTotal() * 100 : 0;
        userStats.setActivePercentage(Math.round(activePercentage * 100.0) / 100.0);

        // Obtener datos de productos
        ProductStatsDto productStats = getProductGeneralStats();
        List<ProductsByCategoryDto> productsByCategory = getProductsByCategory();
        List<TopSellingProductDto> topSellingProducts = getTopSellingProducts(5);

        // Crear métricas del sistema
        SystemMetrics systemMetrics = new SystemMetrics();
        systemMetrics.setServiceStatus(checkServicesStatus());
        systemMetrics.setTotalRequests(calculateTotalRequests());
        systemMetrics.setUptimePercentage(calculateUptimePercentage());

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Crear y retornar el dashboard response
        DashboardResponse dashboard = new DashboardResponse();
        dashboard.setUserStats(userStats);
        dashboard.setSystemMetrics(systemMetrics);
        dashboard.setProductStats(productStats);
        dashboard.setProductsByCategory(productsByCategory);
        dashboard.setTopSellingProducts(topSellingProducts);
        dashboard.setTimestamp(timestamp);

        log.info("Dashboard generado exitosamente - Timestamp: {}", timestamp);
        return dashboard;
    }

    private String checkServicesStatus() {
        boolean userServiceUp = checkServiceStatus(userAuthServiceUrl + "/health");
        boolean commerceServiceUp = checkServiceStatus(commerceServiceUrl + "/health");

        if (userServiceUp && commerceServiceUp) return "OPERATIONAL";
        if (userServiceUp || commerceServiceUp) return "DEGRADED";
        return "DOWN";
    }

    private boolean checkServiceStatus(String healthUrl) {
        try {
            restTemplate.getForObject(healthUrl, String.class);
            return true;
        } catch (Exception e) {
            log.warn("Servicio no disponible: {}", healthUrl);
            return false;
        }
    }

    private long calculateTotalRequests() {
        return 1247L;
    }

    private double calculateUptimePercentage() {
        return 99.95;
    }
}