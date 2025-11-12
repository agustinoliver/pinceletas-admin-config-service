package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.controllers;

import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.entities.TerminosCondicionesEntity;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.services.TerminosCondicionesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/terminos-condiciones")
@RequiredArgsConstructor
public class TerminosCondicionesController {

    private final TerminosCondicionesService terminosCondicionesService;

    @GetMapping
    public List<TerminosCondicionesEntity> listar() {
        return terminosCondicionesService.listar();
    }

    @GetMapping("/{id}")
    public TerminosCondicionesEntity obtener(@PathVariable Long id) {
        return terminosCondicionesService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public TerminosCondicionesEntity crear(@RequestBody TerminosCondicionesEntity terminosCondiciones) {
        terminosCondiciones.setId(null);
        return terminosCondicionesService.guardar(terminosCondiciones);
    }

    @PutMapping("/{id}")
    public TerminosCondicionesEntity actualizar(@PathVariable Long id, @RequestBody TerminosCondicionesEntity terminosCondiciones) {
        terminosCondiciones.setId(id);
        return terminosCondicionesService.guardar(terminosCondiciones);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        terminosCondicionesService.eliminar(id);
    }
}
