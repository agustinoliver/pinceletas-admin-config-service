package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para el reporte de pedidos por estado en el admin service.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersByStatusDto {

    private String status;
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private Double percentage;
}
