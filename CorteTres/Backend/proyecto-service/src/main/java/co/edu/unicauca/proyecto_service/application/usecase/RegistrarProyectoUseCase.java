package co.edu.unicauca.proyecto_service.application.usecase;

import co.edu.unicauca.proyecto_service.domain.model.EstadoProyecto;
import co.edu.unicauca.proyecto_service.domain.model.Proyecto;
import co.edu.unicauca.proyecto_service.domain.ports.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
// Caso de uso que implementa la lógica de registrar proyectos y operaciones
// básicas
// Aplica patrón Facade para exponer operaciones y separar lógica de dominio y
// aplicación
public class RegistrarProyectoUseCase {

    private final ProyectoRepository repository;

    public RegistrarProyectoUseCase(ProyectoRepository repository) {
        this.repository = repository;
    }

    // Registra un nuevo proyecto, asignando estado inicial RECIBIDO
    public Proyecto registrar(Proyecto proyecto) {
        if (proyecto.getId() == null || proyecto.getId().isEmpty()) {
            proyecto.setId(UUID.randomUUID().toString()); // Generar ID si no viene
        }
        proyecto.setEstado(EstadoProyecto.RECIBIDO);
        return repository.save(proyecto);
    }

    // Lista todos los proyectos
    public List<Proyecto> listar() {
        return repository.findAll();
    }

    // Busca proyecto por ID
    public Optional<Proyecto> buscarPorId(String id) {
        return repository.findById(id);
    }

    // Cambia estado y añade comentario (por ejemplo, coordinador)
    public Proyecto cambiarEstado(String id, EstadoProyecto nuevoEstado, String comentario) {
        Proyecto proyecto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));
        proyecto.setEstado(nuevoEstado);
        proyecto.setComentarios(comentario);
        return repository.save(proyecto);
    }

    // Elimina proyecto por ID
    public void eliminar(String id) {
        repository.deleteById(id);
    }

    public List<Proyecto> listarPorEmpresa(String empresaId) {
        return repository.buscarPorEmpresaId(empresaId);
    }

    public Proyecto editar(String id, Proyecto nuevosDatos) {
        Proyecto existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

        // Actualiza los campos editables
        existente.setNombre(nuevosDatos.getNombre());
        existente.setResumen(nuevosDatos.getResumen());
        existente.setObjetivos(nuevosDatos.getObjetivos());
        existente.setDescripcion(nuevosDatos.getDescripcion());
        existente.setTiempoMaxMeses(nuevosDatos.getTiempoMaxMeses());
        existente.setPresupuesto(nuevosDatos.getPresupuesto());
        existente.setFecha(nuevosDatos.getFecha());
        existente.setEmpresaNit(nuevosDatos.getEmpresaNit());

        // Nota: mantenemos el estado y comentarios existentes (no los sobrescribimos)
        return repository.save(existente);
    }

}
