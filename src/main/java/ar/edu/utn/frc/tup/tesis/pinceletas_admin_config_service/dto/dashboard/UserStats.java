package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Estadísticas específicas de usuarios.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStats {

    private long totalUsers;
    private long activeUsers;
    private long inactiveUsers;
    private double activePercentage;
}
