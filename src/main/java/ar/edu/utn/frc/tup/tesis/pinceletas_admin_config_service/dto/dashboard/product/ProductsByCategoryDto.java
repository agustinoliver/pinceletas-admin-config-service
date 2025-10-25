package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.dto.dashboard.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsByCategoryDto {

    private String categoryName;
    private Long categoryId;
    private Long totalProducts;
    private Long activeProducts;
    private Long inactiveProducts;
}
