package co.edu.unicauca.empresa_service.infrastructure.pesistence;

// ---------------------------------------------
// EmpresaJpaEntity.java (Entidad JPA - Infraestructura)
// Representa la empresa en la base de datos (adaptador de salida)
// Utiliza Builder (Creacional) y la anotación @Entity de JPA
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empresas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // También aplica el patrón Builder para facilitar construcción
public class EmpresaJpaEntity {
    @Id
    private String nit;
    private String nombre;
    private String email;
    private String sector;
    private String telefono;
    private String nombresContacto;
    private String apellidosContacto;
    private String cargoContacto;
}