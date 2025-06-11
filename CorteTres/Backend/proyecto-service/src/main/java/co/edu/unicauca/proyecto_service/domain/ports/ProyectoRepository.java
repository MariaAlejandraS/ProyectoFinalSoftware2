package co.edu.unicauca.proyecto_service.domain.ports;

import co.edu.unicauca.proyecto_service.domain.model.Proyecto;
import co.edu.unicauca.proyecto_service.domain.model.EstadoProyecto;

import java.util.List;
import java.util.Optional;

// Puerto que define el contrato para la persistencia de proyectos
// Aplica el patrón Repository (DDD)
public interface ProyectoRepository {

    Proyecto save(Proyecto proyecto); // Guarda o actualiza un proyecto

    Optional<Proyecto> findById(String id); // Buscar proyecto por id

    List<Proyecto> findAll(); // Listar todos los proyectos

    List<Proyecto> findByEstado(EstadoProyecto estado); // Filtrar proyectos por estado

    void deleteById(String id); // Eliminar proyecto por id

    List<Proyecto> buscarPorEmpresaId(String empresaNit);

}
