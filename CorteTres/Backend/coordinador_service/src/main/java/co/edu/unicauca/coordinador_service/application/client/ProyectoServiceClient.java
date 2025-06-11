package co.edu.unicauca.coordinador_service.application.client;

import co.edu.unicauca.coordinador_service.domain.model.Proyecto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProyectoServiceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8081/d/proyecto";

    public ProyectoServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Proyecto> listarProyectos(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<Proyecto>> response = restTemplate.exchange(
                    baseUrl,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Proyecto>>() {
                    });
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al consumir proyecto-service: " + e.getMessage());
            e.printStackTrace(); // muestra la excepción real
            throw new RuntimeException("Fallo al listar proyectos", e);
        }
    }

    public Proyecto obtenerProyectoPorId(String id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Proyecto> response = restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.GET,
                entity,
                Proyecto.class);
        return response.getBody();
    }
}
