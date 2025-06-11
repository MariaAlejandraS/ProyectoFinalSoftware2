package co.edu.unicauca.estudiante_service.adapters.output;

import co.edu.unicauca.estudiante_service.domain.model.ProyectoInfoDTO;
import co.edu.unicauca.estudiante_service.domain.port.ProyectoServicePort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProyectoRestAdapter implements ProyectoServicePort {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8081/d/proyecto";

    public ProyectoRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ProyectoInfoDTO> listarProyectos(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<ProyectoInfoDTO>> response = restTemplate.exchange(
                    baseUrl,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<ProyectoInfoDTO>>() {
                    });
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al consumir proyecto-service desde estudiante: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Fallo al listar proyectos", e);
        }
    }

    @Override
    public ProyectoInfoDTO obtenerProyectoPorId(String proyectoId) {
        String url = "http://localhost:8084/proyectos/" + proyectoId;
        return restTemplate.getForObject(url, ProyectoInfoDTO.class);
    }

}
