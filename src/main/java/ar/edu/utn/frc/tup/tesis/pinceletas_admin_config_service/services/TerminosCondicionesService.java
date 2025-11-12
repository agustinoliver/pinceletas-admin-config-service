package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.services;

import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.entities.TerminosCondicionesEntity;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.repositories.TerminosCondicionesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TerminosCondicionesService {

    private final TerminosCondicionesRepository terminosCondicionesRepository;

    public List<TerminosCondicionesEntity> listar() {
        return terminosCondicionesRepository.findAll();
    }

    public Optional<TerminosCondicionesEntity> buscarPorId(Long id) {
        return terminosCondicionesRepository.findById(id);
    }

    public TerminosCondicionesEntity guardar(TerminosCondicionesEntity terminosCondiciones) {
        return terminosCondicionesRepository.save(terminosCondiciones);
    }

    public void eliminar(Long id) {
        terminosCondicionesRepository.deleteById(id);
    }
}
