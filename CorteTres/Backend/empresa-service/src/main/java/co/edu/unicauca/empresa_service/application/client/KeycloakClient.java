package co.edu.unicauca.empresa_service.application.client;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class KeycloakClient {
    private final RestTemplate restTemplate;
    private final String keycloakBaseUrl = "http://localhost:8080";
    private final String realm = "sistema";
    private final String adminUsername = "admin";
    private final String adminPassword = "123";
    private final String clientId = "sistema-desktop";

    public KeycloakClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void crearUsuarioEmpresa(String nombre, String password) {
        String accessToken = obtenerTokenAdmin(); // Paso previo: obtener token del admin

        // 1. Crear usuario
        String createUserUrl = keycloakBaseUrl + "/admin/realms/" + realm + "/users";

        Map<String, Object> userPayload = Map.of(
                "username", nombre,
                // "email", email,
                "enabled", true,
                "credentials", List.of(Map.of(
                        "type", "password",
                        "value", password,
                        "temporary", false)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(createUserUrl, request, Void.class);

        // 2. Obtener ID del usuario recién creado
        String location = response.getHeaders().getLocation().toString(); // contiene URL con el ID del usuario
        String userId = location.substring(location.lastIndexOf("/") + 1);

        // 3. Obtener ID del rol "company"
        String roleUrl = "http://localhost:8080/admin/realms/sistema/roles/company";
        ResponseEntity<Map> roleResponse = restTemplate.exchange(roleUrl, HttpMethod.GET, new HttpEntity<>(headers),
                Map.class);
        String roleName = (String) roleResponse.getBody().get("name");
        String roleId = (String) roleResponse.getBody().get("id");

        Map<String, Object> rolePayload = Map.of(
                "id", roleId,
                "name", roleName);

        // 4. Asignar rol al usuario
        String assignRoleUrl = "http://localhost:8080/admin/realms/sistema/users/" + userId
                + "/role-mappings/realm";
        HttpEntity<List<Map<String, Object>>> roleRequest = new HttpEntity<>(List.of(rolePayload), headers);
        restTemplate.postForEntity(assignRoleUrl, roleRequest, Void.class);
    }

    public String obtenerTokenAdmin() {
        String tokenUrl = keycloakBaseUrl + "/realms/master/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", "sistema-desktop");
        body.add("username", "admin");
        body.add("password", "123");
        body.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        return response.getBody().get("access_token").toString();
    }

}
