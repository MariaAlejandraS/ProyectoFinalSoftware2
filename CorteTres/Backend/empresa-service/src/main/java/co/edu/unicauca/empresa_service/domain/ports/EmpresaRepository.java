package co.edu.unicauca.empresa_service.domain.ports;

// ---------------------------------------------
// EmpresaRepository.java (Puerto - Interface de Dominio)
// Define un contrato abstracto de persistencia
// Aplica el patrón Repository (DDD)
import co.edu.unicauca.empresa_service.domain.model.*;
import java.util.List;
import java.util.Optional;

public interface EmpresaRepository {
    Empresa save(Empresa empresa);

    List<Empresa> findAll();

    Optional<Empresa> findByNit(String nit);


    void deleteByNit(String nit);

    

}
