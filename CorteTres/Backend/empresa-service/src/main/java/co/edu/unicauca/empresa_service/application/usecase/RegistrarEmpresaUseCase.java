package co.edu.unicauca.empresa_service.application.usecase;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.empresa_service.domain.model.*;
import co.edu.unicauca.empresa_service.domain.ports.*;
import co.edu.unicauca.empresa_service.application.client.*;

public class RegistrarEmpresaUseCase {
    private final EmpresaRepository repository; // Inyección de puerto (Hexagonal Architecture)
    private final KeycloakClient keycloakClient;

    public RegistrarEmpresaUseCase(EmpresaRepository repository, KeycloakClient keycloakClient) {
        this.repository = repository;
        this.keycloakClient = keycloakClient;
    }

    public Empresa registrarConUsuarioKeycloak(Empresa empresa, String password) {
        Optional<Empresa> existente = repository.findByNit(empresa.getNit());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe una empresa con ese NIT");
        }

        Empresa guardada = repository.save(empresa);
        keycloakClient.crearUsuarioEmpresa(empresa.getEmail(), password);
        return guardada;
    }

    public List<Empresa> listar() {
        return repository.findAll();
    }

    public Empresa actualizar(String nit, Empresa nuevaEmpresa) {
        Empresa existente = repository.findByNit(nit)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));

        Empresa actualizada = Empresa.builder()
                .nit(nit) // se conserva
                .nombre(nuevaEmpresa.getNombre())
                .email(nuevaEmpresa.getEmail())
                .sector(nuevaEmpresa.getSector())
                .telefono(nuevaEmpresa.getTelefono())
                .nombresContacto(nuevaEmpresa.getNombresContacto())
                .apellidosContacto(nuevaEmpresa.getApellidosContacto())
                .cargoContacto(nuevaEmpresa.getCargoContacto())
                .build();

        return repository.save(actualizada);
    }

    public void eliminar(String nit) {
        Empresa empresa = repository.findByNit(nit)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));
        repository.deleteByNit(nit);
    }

}
