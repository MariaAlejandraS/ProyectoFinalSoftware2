package co.edu.unicauca.coordinador_service.application.usecase;

import co.edu.unicauca.coordinador_service.domain.model.*;
import co.edu.unicauca.coordinador_service.domain.ports.ProyectoEstadoRepository;
import co.edu.unicauca.coordinador_service.domain.service.Notificador;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CambiarEstadoUseCase {

    private final ProyectoEstadoRepository repository;
    private final Notificador notificador;

    public CambiarEstadoUseCase(ProyectoEstadoRepository repository, Notificador notificador) {
        this.repository = repository;
        this.notificador = notificador;
    }

    /**
     * Cambia el estado de un proyecto, guarda comentarios y notifica a interesados.
     * Aplica patrón Strategy para posibles diferentes reglas de cambio.
     * Aplica patrón Observer para notificar cambios.
     */
    public ProyectoEstado cambiarEstado(String proyectoId, EstadoProyecto nuevoEstado, String comentario,
            String periodo) {
        ProyectoEstado estadoActual = repository.findByProyectoId(proyectoId)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

        // Validar transición
        EstadoProyecto estadoAnterior = estadoActual.getEstado();
        if (!estadoAnterior.puedeTransicionarA(nuevoEstado)) {
            throw new IllegalStateException("No se puede cambiar de " + estadoAnterior + " a " + nuevoEstado);
        }

        // Aplicar cambio
        estadoActual.setEstado(nuevoEstado);
        estadoActual.setComentarios(comentario);
        estadoActual.setFechaEvaluacion(LocalDate.now());
        estadoActual.setPeriodoAcademico(periodo);

        ProyectoEstado actualizado = repository.save(estadoActual);

        // Notificar usando patrón Observer
        notificador.notificarCambioEstado(proyectoId, nuevoEstado.name(), comentario);

        return actualizado;
    }

}