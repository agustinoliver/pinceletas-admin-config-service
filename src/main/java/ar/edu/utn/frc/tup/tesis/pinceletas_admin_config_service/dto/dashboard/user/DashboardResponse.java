package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.user;

import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.product.ProductStatsDto;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.product.ProductsByCategoryDto;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.product.TopSellingProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para el dashboard administrativo con múltiples métricas del sistema.
 * Agrupa diferentes estadísticas para visualización en paneles de control.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {

    /** Estadísticas de usuarios del sistema. */
    private UserStats userStats;

    /** Métricas generales del sistema. */
    private SystemMetrics systemMetrics;

    /** Timestamp de generación del reporte. */
    private String timestamp;

    // 🆕 NUEVOS CAMPOS PARA COMMERCE

    /** Estadísticas generales de productos */
    private ProductStatsDto productStats;

    /** Lista de productos por categoría */
    private List<ProductsByCategoryDto> productsByCategory;

    /** Lista de productos más vendidos (top 5 por defecto) */
    private List<TopSellingProductDto> topSellingProducts;
}
