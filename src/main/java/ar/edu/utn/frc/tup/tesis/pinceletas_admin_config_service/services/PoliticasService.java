package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.services;

import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.entities.PoliticasEntity;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.repositories.PoliticasRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PoliticasService {

    private final PoliticasRepository politicasRepository;

    public List<PoliticasEntity> listar() {
        return politicasRepository.findAll();
    }

    public Optional<PoliticasEntity> buscarPorId(Long id) {
        return politicasRepository.findById(id);
    }

    public PoliticasEntity guardar(PoliticasEntity politicas) {
        return politicasRepository.save(politicas);
    }

    public void eliminar(Long id) {
        politicasRepository.deleteById(id);
    }
}
