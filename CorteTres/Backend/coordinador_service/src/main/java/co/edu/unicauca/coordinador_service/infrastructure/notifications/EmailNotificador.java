package co.edu.unicauca.coordinador_service.infrastructure.notifications;


import co.edu.unicauca.coordinador_service.domain.service.Notificador;
import org.springframework.stereotype.Component;

@Component // Spring singleton por defecto
public class EmailNotificador implements Notificador {

    @Override
    public void notificarCambioEstado(String proyectoId, String estadoNuevo, String comentarios) {
        // Aquí integrarías un servicio real de correo
        System.out.println("Enviando email: Proyecto " + proyectoId + " cambió a estado " + estadoNuevo +
                " con comentario: " + comentarios);
    }
}