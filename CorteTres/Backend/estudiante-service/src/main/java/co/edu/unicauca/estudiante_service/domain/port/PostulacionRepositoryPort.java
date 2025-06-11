package co.edu.unicauca.estudiante_service.domain.port;

import java.util.List;

import co.edu.unicauca.estudiante_service.domain.model.Postulacion;

public interface PostulacionRepositoryPort {
    Postulacion guardar(Postulacion p);

    List<Postulacion> buscarPorEstudiante(String estudianteId);

    List<Postulacion> findByEstudianteId(String estudianteId);

    List<Postulacion> findAll();

}
