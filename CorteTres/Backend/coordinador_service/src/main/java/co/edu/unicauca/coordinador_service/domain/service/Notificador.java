package co.edu.unicauca.coordinador_service.domain.service;
public interface Notificador {
    void notificarCambioEstado(String proyectoId, String estadoNuevo, String comentarios);
}