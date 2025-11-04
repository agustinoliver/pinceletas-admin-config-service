package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.repositories;

import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.entities.TiendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiendaRepository extends JpaRepository<TiendaEntity, Long> {
}
