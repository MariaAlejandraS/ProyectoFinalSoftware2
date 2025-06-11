package co.edu.unicauca.coordinador_service.domain.ports;

import co.edu.unicauca.coordinador_service.domain.model.ProyectoEstado;

import java.util.List;
import java.util.Optional;

public interface ProyectoEstadoRepository {
    ProyectoEstado save(ProyectoEstado proyectoEstado);
    Optional<ProyectoEstado> findByProyectoId(String proyectoId);
    List<ProyectoEstado> findAll();
    void deleteByProyectoId(String proyectoId);
}

