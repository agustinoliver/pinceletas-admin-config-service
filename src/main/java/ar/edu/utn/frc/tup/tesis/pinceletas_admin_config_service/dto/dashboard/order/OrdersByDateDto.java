package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para el reporte de pedidos por fecha en el admin service.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersByDateDto {

    private LocalDate date;
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private Long completedOrders;
    private Long cancelledOrders;
    private Long pendingOrders;
}
