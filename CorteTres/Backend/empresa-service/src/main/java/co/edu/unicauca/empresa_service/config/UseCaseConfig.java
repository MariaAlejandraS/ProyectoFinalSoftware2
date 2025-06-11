package co.edu.unicauca.empresa_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.empresa_service.application.client.KeycloakClient;
import co.edu.unicauca.empresa_service.application.usecase.RegistrarEmpresaUseCase;
import co.edu.unicauca.empresa_service.domain.ports.EmpresaRepository;

@Configuration
public class UseCaseConfig {

    @Bean
    public RegistrarEmpresaUseCase registrarEmpresaUseCase(EmpresaRepository repository,
            KeycloakClient keycloakClient) {
        return new RegistrarEmpresaUseCase(repository, keycloakClient);
    }
}
