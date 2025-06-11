package co.edu.unicauca.coordinador_service.application.client;

import co.edu.unicauca.coordinador_service.domain.model.PostulacionConProyectoDTO;
import co.edu.unicauca.coordinador_service.domain.model.Proyecto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EstudianteServiceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8081/c/student";

    public EstudianteServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // EstudianteServiceClient.java
    public List<PostulacionConProyectoDTO> obtenerTodasLasPostulaciones(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = baseUrl + "/postulaciones/proyectos";

        ResponseEntity<List<PostulacionConProyectoDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<PostulacionConProyectoDTO>>() {
                });

        return response.getBody();
    }

}
