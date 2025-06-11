// ---------------------------------------------
// CoordinadorController.java
// Controlador REST que actúa como fachada (Patrón Facade)
// Consume Proyecto Service de forma segura (enviando token JWT)
// Aplica separación de responsabilidades y seguridad basada en roles
// ---------------------------------------------

package co.edu.unicauca.coordinador_service.adapters.web;

import co.edu.unicauca.coordinador_service.application.client.ProyectoServiceClient;
import co.edu.unicauca.coordinador_service.application.usecase.CambiarEstadoUseCase;
import co.edu.unicauca.coordinador_service.application.usecase.CoordinadorUseCase;
import co.edu.unicauca.coordinador_service.application.usecase.ObtenerEstadisticasUseCase;
import co.edu.unicauca.coordinador_service.domain.model.EstadoProyecto;
import co.edu.unicauca.coordinador_service.domain.model.PostulacionConProyectoDTO;
import co.edu.unicauca.coordinador_service.domain.model.ProyectoEstado;
import co.edu.unicauca.coordinador_service.domain.model.Proyecto;
import co.edu.unicauca.coordinador_service.domain.model.EstadisticasDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coordinator")
public class CoordinadorController {

    private final CambiarEstadoUseCase cambiarEstadoUseCase;
    private final ProyectoServiceClient proyectoServiceClient;
    private final ObtenerEstadisticasUseCase obtenerEstadisticasUseCase;
    private final CoordinadorUseCase useCase;

    public CoordinadorController(CambiarEstadoUseCase cambiarEstadoUseCase,
            ProyectoServiceClient proyectoServiceClient,
            ObtenerEstadisticasUseCase obtenerEstadisticasUseCase, CoordinadorUseCase useCase) {
        this.cambiarEstadoUseCase = cambiarEstadoUseCase;
        this.proyectoServiceClient = proyectoServiceClient;
        this.obtenerEstadisticasUseCase = obtenerEstadisticasUseCase;
        this.useCase = useCase;
    }

    @PutMapping("/proyectos/{id}/estado")
    @PreAuthorize("hasRole('coordinator')")
    public ResponseEntity<ProyectoEstado> cambiarEstado(
            @PathVariable("id") String id,
            @RequestParam EstadoProyecto estado,
            @RequestParam String periodo,
            @RequestParam(required = false) String comentario) {

        ProyectoEstado actualizado = cambiarEstadoUseCase.cambiarEstado(id, estado, comentario, periodo);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/proyectos/{id}")
    @PreAuthorize("hasRole('coordinator')")
    public Proyecto obtenerProyecto(@PathVariable("id") String id,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return proyectoServiceClient.obtenerProyectoPorId(id, token);
    }

    @GetMapping("/estadisticas")
    @PreAuthorize("hasRole('coordinator')")
    public ResponseEntity<EstadisticasDTO> obtenerEstadisticas(@RequestParam("periodo") String periodo) {
        EstadisticasDTO stats = obtenerEstadisticasUseCase.calcularEstadisticas(periodo);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/estadisticas/periodos")
    @PreAuthorize("hasRole('coordinator')")
    public ResponseEntity<List<String>> listarPeriodos() {
        // Puedes obtenerlos dinámicamente o por ahora usar una lista fija
        List<String> periodos = List.of("2024-1", "2024-2", "2025-1", "2025-2");
        return ResponseEntity.ok(periodos);
    }

    @GetMapping("/proyectos")
    @PreAuthorize("hasRole('coordinator')")
    public List<Proyecto> listarProyectos(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        try {
            List<Proyecto> proyectos = proyectoServiceClient.listarProyectos(token);
            System.out.println("Proyectos recibidos: " + proyectos.size());
            return proyectos;
        } catch (Exception e) {
            System.err.println("Error al listar proyectos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/postulaciones/proyectos")
    @PreAuthorize("hasRole('coordinator')")
    public ResponseEntity<List<PostulacionConProyectoDTO>> obtenerPostulaciones(
            @RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        return ResponseEntity.ok(useCase.obtenerTodasLasPostulaciones(jwt));
    }

}