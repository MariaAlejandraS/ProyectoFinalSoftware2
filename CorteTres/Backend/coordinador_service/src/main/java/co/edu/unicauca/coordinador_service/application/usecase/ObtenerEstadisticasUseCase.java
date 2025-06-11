package co.edu.unicauca.coordinador_service.application.usecase;

import co.edu.unicauca.coordinador_service.domain.model.EstadisticasDTO;
import co.edu.unicauca.coordinador_service.domain.model.ProyectoEstado;
import co.edu.unicauca.coordinador_service.domain.model.EstadoProyecto;
import co.edu.unicauca.coordinador_service.domain.ports.ProyectoEstadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerEstadisticasUseCase {

    private final ProyectoEstadoRepository repository;

    public ObtenerEstadisticasUseCase(ProyectoEstadoRepository repository) {
        this.repository = repository;
    }

    public EstadisticasDTO calcularEstadisticas(String periodo) {
        List<ProyectoEstado> todos = repository.findAll()
                .stream()
                .filter(p -> p.getPeriodoAcademico().equalsIgnoreCase(periodo))
                .toList();

        long recibidos = todos.stream().filter(p -> p.getEstado() == EstadoProyecto.RECIBIDO).count();
        long aceptados = todos.stream().filter(p -> p.getEstado() == EstadoProyecto.ACEPTADO).count();
        long rechazados = todos.stream().filter(p -> p.getEstado() == EstadoProyecto.RECHAZADO).count();
        long terminados = todos.stream().filter(p -> p.getEstado() == EstadoProyecto.TERMINADO).count();

        return EstadisticasDTO.builder()
                .totalProyectos(todos.size())
                .recibidos(recibidos)
                .aceptados(aceptados)
                .rechazados(rechazados)
                .terminados(terminados)
                .build();
    }

}
