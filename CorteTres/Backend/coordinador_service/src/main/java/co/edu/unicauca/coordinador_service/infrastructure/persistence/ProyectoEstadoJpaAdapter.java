package co.edu.unicauca.coordinador_service.infrastructure.persistence;

import co.edu.unicauca.coordinador_service.domain.model.ProyectoEstado;
import co.edu.unicauca.coordinador_service.domain.ports.ProyectoEstadoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// ---------------------------------------------
// ProyectoEstadoJpaAdapter.java
// Adaptador de salida (Infraestructura)
// Aplica el patrón Adapter para traducir entre el dominio y la base de datos (JPA)
@Repository
public class ProyectoEstadoJpaAdapter implements ProyectoEstadoRepository {

    private final ProyectoEstadoJpaRepository jpaRepository;

    public ProyectoEstadoJpaAdapter(ProyectoEstadoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ProyectoEstado save(ProyectoEstado proyectoEstado) {
        ProyectoEstadoJpaEntity entity = toEntity(proyectoEstado);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<ProyectoEstado> findByProyectoId(String proyectoId) {
        return jpaRepository.findByProyectoId(proyectoId).map(this::toDomain);
    }

    @Override
    public List<ProyectoEstado> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteByProyectoId(String proyectoId) {
        jpaRepository.deleteByProyectoId(proyectoId);
    }

    // Conversión de entidad JPA a objeto de dominio
    private ProyectoEstado toDomain(ProyectoEstadoJpaEntity e) {
        return ProyectoEstado.builder()
                .proyectoId(e.getProyectoId())
                .estado(e.getEstado())
                .comentarios(e.getComentarios())
                .fechaEvaluacion(e.getFechaEvaluacion())
                .periodoAcademico(e.getPeriodoAcademico())
                .build();
    }

    // Conversión de objeto de dominio a entidad JPA
    private ProyectoEstadoJpaEntity toEntity(ProyectoEstado p) {
        return ProyectoEstadoJpaEntity.builder()
                .proyectoId(p.getProyectoId())
                .estado(p.getEstado())
                .comentarios(p.getComentarios())
                .fechaEvaluacion(p.getFechaEvaluacion())
                .periodoAcademico(p.getPeriodoAcademico())
                .build();
    }
}
