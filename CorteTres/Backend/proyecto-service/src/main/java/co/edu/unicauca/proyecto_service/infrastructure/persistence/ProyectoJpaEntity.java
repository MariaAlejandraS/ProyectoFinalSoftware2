package co.edu.unicauca.proyecto_service.infrastructure.persistence;

import co.edu.unicauca.proyecto_service.domain.model.EstadoProyecto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "proyectos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProyectoJpaEntity {

    @Id
    private String id;

    private String nombre;
    private String resumen;
    private String objetivos;
    private String descripcion;
    private int tiempoMaxMeses;
    private double presupuesto;
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private EstadoProyecto estado;

    private String comentarios;
    private String empresaNit;
}
