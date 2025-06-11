package co.edu.unicauca.coordinador_service.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@Builder // Builder (Creacional) para construcción flexible y segura
public class ProyectoEstado {
    @NonNull
    private String proyectoId;
    @NonNull
    private EstadoProyecto estado;
    private String comentarios;
    @NonNull
    private LocalDate fechaEvaluacion;
    @NonNull
    private String periodoAcademico;
}
