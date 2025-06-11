package co.edu.unicauca.coordinador_service.domain.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PostulacionConProyectoDTO {
    private String id;
    private String estudianteId;
    private String proyectoId;
    private LocalDate fecha;
    private Proyecto proyecto;
}
