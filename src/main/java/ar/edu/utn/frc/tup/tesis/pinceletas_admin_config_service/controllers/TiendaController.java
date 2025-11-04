package ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.controllers;

import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.entities.TiendaEntity;
import ar.edu.utn.frc.tup.tesis.pinceletas_admin_config_service.services.TiendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tiendas")
@RequiredArgsConstructor
public class TiendaController {

    private final TiendaService tiendaService;

    @GetMapping
    public List<TiendaEntity> listar() {
        return tiendaService.listar();
    }

    @GetMapping("/{id}")
    public TiendaEntity obtener(@PathVariable Long id) {
        return tiendaService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public TiendaEntity crear(@RequestBody TiendaEntity tienda) {
        tienda.setId(null);
        return tiendaService.guardar(tienda);
    }

    @PutMapping("/{id}")
    public TiendaEntity actualizar(@PathVariable Long id, @RequestBody TiendaEntity tienda) {
        tienda.setId(id);
        return tiendaService.guardar(tienda);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tiendaService.eliminar(id);
    }
}
