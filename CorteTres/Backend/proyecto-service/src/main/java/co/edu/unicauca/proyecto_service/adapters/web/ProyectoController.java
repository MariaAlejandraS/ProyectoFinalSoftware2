package co.edu.unicauca.proyecto_service.adapters.web;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.proyecto_service.application.usecase.RegistrarProyectoUseCase;
import co.edu.unicauca.proyecto_service.domain.model.EstadoProyecto;
import co.edu.unicauca.proyecto_service.domain.model.Proyecto;

// Patrón Facade: expone la lógica de aplicación como API REST simplificada para el cliente
@RestController
@RequestMapping("/proyecto")
public class ProyectoController {

    private final RegistrarProyectoUseCase useCase;

    public ProyectoController(RegistrarProyectoUseCase useCase) {
        this.useCase = useCase;
    }

    // Cambiar estado del proyecto
    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('coordinator')")
    public Proyecto cambiarEstado(@PathVariable String id,
            @RequestParam EstadoProyecto estado,
            @RequestParam(required = false) String comentario) {
        return useCase.cambiarEstado(id, estado, comentario);
    }

    // Registrar nuevo proyecto - Solo empresas autorizada

    @PostMapping
    @PreAuthorize("hasRole('company')")
    public Proyecto registrar(@RequestBody Proyecto proyecto) {
        // Si el id es null o vacío, genera uno nuevo
        if (proyecto.getId() == null || proyecto.getId().isEmpty()) {
            proyecto.setId(UUID.randomUUID().toString());
        }
        return useCase.registrar(proyecto);
    }

    // Eliminar proyecto
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('company')")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        useCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todos los proyectos
    @GetMapping
    public List<Proyecto> listar() {
        return useCase.listar();
    }

    // Obtener detalles de proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> detalles(@PathVariable String id) {
        Optional<Proyecto> proyecto = useCase.buscarPorId(id);
        return proyecto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Listar proyectos por empresa
    @GetMapping("/empresa/{empresaId}")
    @PreAuthorize("hasRole('company')")
    public List<Proyecto> listarPorEmpresa(@PathVariable String empresaId) {
        return useCase.listarPorEmpresa(empresaId);
    }

    // Editar proyectos
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('company')")
    public ResponseEntity<Void> editar(@PathVariable String id, @RequestBody Proyecto proyecto) {
        useCase.editar(id, proyecto);
        return ResponseEntity.noContent().build();
    }

}
