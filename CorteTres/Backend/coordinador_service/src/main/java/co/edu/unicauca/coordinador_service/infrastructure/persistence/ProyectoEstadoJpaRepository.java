package co.edu.unicauca.coordinador_service.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProyectoEstadoJpaRepository extends JpaRepository<ProyectoEstadoJpaEntity, String> {
    Optional<ProyectoEstadoJpaEntity> findByProyectoId(String proyectoId);
    void deleteByProyectoId(String proyectoId);
}