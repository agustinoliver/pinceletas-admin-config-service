package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Métricas generales del sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemMetrics {

    private String serviceStatus;
    private long totalRequests;
    private double uptimePercentage;
}
