package co.edu.unicauca.estudiante_service.application.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.edu.unicauca.estudiante_service.domain.model.Postulacion;
import co.edu.unicauca.estudiante_service.domain.model.PostulacionConProyectoDTO;
import co.edu.unicauca.estudiante_service.domain.model.ProyectoInfoDTO;
import co.edu.unicauca.estudiante_service.domain.port.PostulacionRepositoryPort;
import co.edu.unicauca.estudiante_service.domain.port.ProyectoServicePort;

@Service
public class EstudianteUseCase {

    private final ProyectoServicePort proyectoPort;
    private final PostulacionRepositoryPort postulacionPort;

    public EstudianteUseCase(ProyectoServicePort proyectoPort, PostulacionRepositoryPort postulacionPort) {
        this.proyectoPort = proyectoPort;
        this.postulacionPort = postulacionPort;
    }

    public List<ProyectoInfoDTO> listarProyectos(String token) {
        return proyectoPort.listarProyectos(token);
    }

    public Postulacion postular(String estudianteId, String proyectoId) {
        Postulacion p = new Postulacion();
        p.setId(UUID.randomUUID().toString());
        p.setEstudianteId(estudianteId);
        p.setProyectoId(proyectoId);
        p.setFecha(LocalDate.now());

        return postulacionPort.guardar(p);
    }

    public List<Postulacion> listarPostulaciones(String estudianteId) {
        return postulacionPort.buscarPorEstudiante(estudianteId);
    }

    public List<PostulacionConProyectoDTO> obtenerTodasLasPostulacionesConProyecto() {
        return postulacionPort.findAll().stream()
                .map(postulacion -> {
                    ProyectoInfoDTO proyecto = proyectoPort.obtenerProyectoPorId(postulacion.getProyectoId());
                    return new PostulacionConProyectoDTO(postulacion, proyecto);

                })
                .collect(Collectors.toList());
    }

}
