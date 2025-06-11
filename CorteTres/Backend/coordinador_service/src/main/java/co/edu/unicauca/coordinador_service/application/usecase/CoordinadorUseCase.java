package co.edu.unicauca.coordinador_service.application.usecase;

import co.edu.unicauca.coordinador_service.application.client.EstudianteServiceClient;
import co.edu.unicauca.coordinador_service.domain.model.PostulacionConProyectoDTO;
import co.edu.unicauca.coordinador_service.domain.model.Proyecto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinadorUseCase {

    private final EstudianteServiceClient estudianteClient;

    public CoordinadorUseCase(EstudianteServiceClient estudianteClient) {
        this.estudianteClient = estudianteClient;
    }

    // CoordinadorUseCase.java
    public List<PostulacionConProyectoDTO> obtenerTodasLasPostulaciones(String token) {
        return estudianteClient.obtenerTodasLasPostulaciones(token);
    }

}
