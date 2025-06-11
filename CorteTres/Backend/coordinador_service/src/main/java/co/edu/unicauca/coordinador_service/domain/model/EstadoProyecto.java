package co.edu.unicauca.coordinador_service.domain.model;

public enum EstadoProyecto {
    RECIBIDO,
    ACEPTADO,
    RECHAZADO,
    TERMINADO;

    /**
     * Valida si se puede cambiar del estado actual a uno nuevo.
     *
     * @param nuevoEstado El estado al que se quiere cambiar.
     * @return true si la transición es válida, false si no lo es.
     */
    public boolean puedeTransicionarA(EstadoProyecto nuevoEstado) {
        switch (this) {
            case RECIBIDO:
                return nuevoEstado == ACEPTADO || nuevoEstado == RECHAZADO;
            case ACEPTADO:
                return nuevoEstado == TERMINADO;
            case RECHAZADO:
                return false; // No se puede avanzar desde RECHAZADO
            case TERMINADO:
                return false; // Estado final
            default:
                return false;
        }
    }
}
