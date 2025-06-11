package co.edu.unicauca.proyecto_service.infrastructure.persistence;

import co.edu.unicauca.proyecto_service.domain.model.EstadoProyecto;
import co.edu.unicauca.proyecto_service.domain.model.Proyecto;
import co.edu.unicauca.proyecto_service.domain.ports.ProyectoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProyectoJpaAdapter implements ProyectoRepository {
    // Patrón Adapter: convierte entre entidades JPA (infraestructura) y objetos de
    // dominio
    // para desacoplar la capa de dominio de la persistencia específica (JPA)

    private final ProyectoJpaRepository jpaRepository;

    public ProyectoJpaAdapter(ProyectoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Proyecto save(Proyecto proyecto) {
        ProyectoJpaEntity entity = toEntity(proyecto);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Proyecto> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Proyecto> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Proyecto> findByEstado(EstadoProyecto estado) {
        return jpaRepository.findByEstado(estado).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        jpaRepository.deleteById(id);
    }

    // Conversión de entidad JPA a dominio
    private Proyecto toDomain(ProyectoJpaEntity e) {
        return Proyecto.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .resumen(e.getResumen())
                .objetivos(e.getObjetivos())
                .descripcion(e.getDescripcion())
                .tiempoMaxMeses(e.getTiempoMaxMeses())
                .presupuesto(e.getPresupuesto())
                .fecha(e.getFecha())
                .estado(e.getEstado())
                .comentarios(e.getComentarios())
                .empresaNit(e.getEmpresaNit())
                .build();
    }

    // Conversión de dominio a entidad JPA
    private ProyectoJpaEntity toEntity(Proyecto p) {
        return ProyectoJpaEntity.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .resumen(p.getResumen())
                .objetivos(p.getObjetivos())
                .descripcion(p.getDescripcion())
                .tiempoMaxMeses(p.getTiempoMaxMeses())
                .presupuesto(p.getPresupuesto())
                .fecha(p.getFecha())
                .estado(p.getEstado())
                .comentarios(p.getComentarios())
                .empresaNit(p.getEmpresaNit())
                .build();
    }

    @Override
    public List<Proyecto> buscarPorEmpresaId(String empresaNit) {
        return jpaRepository.findByEmpresaNit(empresaNit)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

}
