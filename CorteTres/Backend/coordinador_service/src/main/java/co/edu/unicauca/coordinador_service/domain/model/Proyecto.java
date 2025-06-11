package co.edu.unicauca.coordinador_service.domain.model;

import lombok.Data;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

@Data
public class Proyecto {
    private String id;
    private String nombre;
    private String resumen;
    private String objetivos;
    private String descripcion;
    private int tiempoMaxMeses;
    private double presupuesto;
    private LocalDate fecha;
    private EstadoProyecto estado;
    private String comentarios;
    private String empresaNit;
}
