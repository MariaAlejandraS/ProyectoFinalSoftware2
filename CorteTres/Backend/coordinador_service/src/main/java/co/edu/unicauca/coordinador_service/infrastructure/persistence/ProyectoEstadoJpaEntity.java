package co.edu.unicauca.coordinador_service.infrastructure.persistence;

import co.edu.unicauca.coordinador_service.domain.model.EstadoProyecto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "proyectos_estado")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProyectoEstadoJpaEntity {

    @Id
    private String proyectoId;

    @Enumerated(EnumType.STRING)
    private EstadoProyecto estado;

    private String comentarios;

    private LocalDate fechaEvaluacion;
    private String periodoAcademico;
}
