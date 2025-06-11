package co.edu.unicauca.proyecto_service.domain.model;

// Enum para representar los posibles estados del proyecto
public enum EstadoProyecto {
    RECIBIDO,   // Estado inicial cuando se registra el proyecto
    ACEPTADO,   // Proyecto aceptado para ejecución
    RECHAZADO,  // Proyecto rechazado
    TERMINADO   // Proyecto finalizado
}
