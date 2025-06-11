package co.edu.unicauca.estudiante_service.adapters.web;

import co.edu.unicauca.estudiante_service.application.usecase.EstudianteUseCase;
import co.edu.unicauca.estudiante_service.domain.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class EstudianteController {

    private final EstudianteUseCase useCase;

    public EstudianteController(EstudianteUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/proyectos")
    public List<ProyectoInfoDTO> listarProyectos(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        return useCase.listarProyectos(jwt);
    }

    @PostMapping("/postular")
    @PreAuthorize("hasRole('student')")
    public Postulacion postular(@RequestParam String estudianteId,
            @RequestParam String proyectoId) {
        return useCase.postular(estudianteId, proyectoId);
    }

    @GetMapping("/{id}/postulaciones")
    public List<Postulacion> listarPostulaciones(@PathVariable String id) {
        return useCase.listarPostulaciones(id);

    }

    @GetMapping("/postulaciones/proyectos")
    @PreAuthorize("hasRole('coordinator')")
    public List<PostulacionConProyectoDTO> obtenerPostulacionesConProyectos() {
        return useCase.obtenerTodasLasPostulacionesConProyecto();
    }

}
