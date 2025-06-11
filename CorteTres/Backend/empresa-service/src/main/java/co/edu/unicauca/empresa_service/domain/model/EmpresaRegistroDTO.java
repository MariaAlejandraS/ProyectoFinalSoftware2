package co.edu.unicauca.empresa_service.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para registrar una empresa junto con la contraseña del usuario Keycloak.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaRegistroDTO {

    @NotNull
    private Empresa empresa;

    @NotNull
    private String password;
}
