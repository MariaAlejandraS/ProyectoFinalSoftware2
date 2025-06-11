package co.edu.unicauca.proyecto_service.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@Builder // Patrón Builder (Creacional) para facilitar la creación flexible de objetos
         // Proyecto
@NoArgsConstructor // Constructor vacío para Jackson
@AllArgsConstructor // Constructor completo para Builder y uso interno
public class Proyecto {

    private String id;

    @NonNull
    private String nombre;

    @NonNull
    private String resumen;

    @NonNull
    private String objetivos;

    private String descripcion;

    @NonNull
    private int tiempoMaxMeses;

    private double presupuesto;

    @NonNull
    private LocalDate fecha;

    @NonNull
    private EstadoProyecto estado;

    private String comentarios;

    @NonNull
    private String empresaNit;

}
