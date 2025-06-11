package co.edu.unicauca.coordinador_service.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstadisticasDTO {
    private long totalProyectos;
    private long recibidos;
    private long aceptados;
    private long rechazados;
    private long terminados;
}
