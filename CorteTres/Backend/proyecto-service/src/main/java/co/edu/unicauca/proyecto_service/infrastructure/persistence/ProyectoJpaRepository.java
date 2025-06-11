package co.edu.unicauca.proyecto_service.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.proyecto_service.domain.model.EstadoProyecto;

public interface ProyectoJpaRepository extends JpaRepository<ProyectoJpaEntity, String> {
    List<ProyectoJpaEntity> findByEstado(EstadoProyecto estado);

    List<ProyectoJpaEntity> findByEmpresaNit(String empresaNit);
}
