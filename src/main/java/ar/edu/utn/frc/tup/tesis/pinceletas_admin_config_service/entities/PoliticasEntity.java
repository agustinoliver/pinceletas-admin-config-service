package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tiendas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoliticasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "politica_devolucion", columnDefinition = "TEXT")
    private String politicaDevolucion;

    @Column(name = "politica_privacidad", columnDefinition = "TEXT")
    private String politicaPrivacidad;
}
