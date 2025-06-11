package co.edu.unicauca.empresa_service.domain.model;
//---------------------------------------------

// Empresa.java (Entidad de dominio - Domain Layer)
// Representa el modelo del dominio según DDD
// Aplica el patrón Builder (Creacional) con Lombok

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
@Builder
public class Empresa {

    @NotBlank(message = "El NIT es obligatorio")
    private String nit;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "El sector es obligatorio")
    private String sector;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{7,10}", message = "El teléfono debe tener entre 7 y 10 dígitos")
    private String telefono;

    @NotBlank(message = "El nombre del contacto es obligatorio")
    private String nombresContacto;

    @NotBlank(message = "El apellido del contacto es obligatorio")
    private String apellidosContacto;

    @NotBlank(message = "El cargo del contacto es obligatorio")
    private String cargoContacto;
}
