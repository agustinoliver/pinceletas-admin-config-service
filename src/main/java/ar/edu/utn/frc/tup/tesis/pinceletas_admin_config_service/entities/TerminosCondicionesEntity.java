package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "terminos_condiciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerminosCondicionesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "terminos_servicio", columnDefinition = "TEXT")
    private String terminosServicio;

    @Column(name = "politica_privacidad", columnDefinition = "TEXT")
    private String politicaPrivacidad;
}
