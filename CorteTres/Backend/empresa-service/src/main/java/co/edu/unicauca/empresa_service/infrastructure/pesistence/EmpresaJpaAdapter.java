package co.edu.unicauca.empresa_service.infrastructure.pesistence;

import org.springframework.stereotype.Repository;
import co.edu.unicauca.empresa_service.domain.model.Empresa;
import co.edu.unicauca.empresa_service.domain.ports.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmpresaJpaAdapter implements EmpresaRepository {
    private final EmpresaJpaRepository repository;

    public EmpresaJpaAdapter(EmpresaJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Empresa save(Empresa empresa) {
        EmpresaJpaEntity entity = EmpresaJpaEntity.builder()
                .nit(empresa.getNit())
                .nombre(empresa.getNombre())
                .email(empresa.getEmail())
                .sector(empresa.getSector())
                .telefono(empresa.getTelefono())
                .nombresContacto(empresa.getNombresContacto())
                .apellidosContacto(empresa.getApellidosContacto())
                .cargoContacto(empresa.getCargoContacto())
                .build();
        return toDomain(repository.save(entity));
    }

    @Override
    public List<Empresa> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Empresa> findByNit(String nit) {
        return repository.findById(nit).map(this::toDomain);
    }

    private Empresa toDomain(EmpresaJpaEntity e) {
        return Empresa.builder()
                .nit(e.getNit())
                .nombre(e.getNombre())
                .email(e.getEmail())
                .sector(e.getSector())
                .telefono(e.getTelefono())
                .nombresContacto(e.getNombresContacto())
                .apellidosContacto(e.getApellidosContacto())
                .cargoContacto(e.getCargoContacto())
                .build();
    }
    @Override
public void deleteByNit(String nit) {
    repository.deleteById(nit);
}

}
