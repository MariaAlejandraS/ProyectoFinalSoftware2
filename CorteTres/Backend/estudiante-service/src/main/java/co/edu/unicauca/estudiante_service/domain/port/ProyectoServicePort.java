package co.edu.unicauca.estudiante_service.domain.port;

import co.edu.unicauca.estudiante_service.domain.model.ProyectoInfoDTO;

import java.util.List;

public interface ProyectoServicePort {
    List<ProyectoInfoDTO> listarProyectos(String token);

    ProyectoInfoDTO obtenerProyectoPorId(String proyectoId);

}
