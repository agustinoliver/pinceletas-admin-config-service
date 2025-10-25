package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSellingProductDto {

    private Long productId;
    private String productName;
    private String categoryName;
    private Long unitsSold;
    private BigDecimal totalRevenue;
    private BigDecimal averagePrice;
}
