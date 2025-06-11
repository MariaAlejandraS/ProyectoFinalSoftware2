package co.edu.unicauca.empresa_service.adapters.web;

// ---------------------------------------------
// EmpresaController.java (Adaptador primario - API REST)
// Expone la lógica como endpoints HTTP
// Patrón Facade (oculta la complejidad del dominio)

import co.edu.unicauca.empresa_service.application.usecase.RegistrarEmpresaUseCase;
import co.edu.unicauca.empresa_service.domain.model.Empresa;
import co.edu.unicauca.empresa_service.domain.model.EmpresaRegistroDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class EmpresaController {

    private final RegistrarEmpresaUseCase useCase;

    public EmpresaController(RegistrarEmpresaUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<Empresa> registrar(@Valid @RequestBody EmpresaRegistroDTO registro) {
        Empresa registrada = useCase.registrarConUsuarioKeycloak(registro.getEmpresa(), registro.getPassword());
        return ResponseEntity.ok(registrada);
    }

    @GetMapping
    @PreAuthorize("hasRole('company')")
    public ResponseEntity<List<Empresa>> listar() {
        List<Empresa> empresas = useCase.listar();
        return ResponseEntity.ok(empresas);
    }

    @PutMapping("/{nit}")
    @PreAuthorize("hasRole('company')")
    public ResponseEntity<Empresa> actualizar(@PathVariable String nit, @Valid @RequestBody Empresa empresa) {
        Empresa actualizada = useCase.actualizar(nit, empresa);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{nit}")
    @PreAuthorize("hasRole('company')")
    public ResponseEntity<Void> eliminar(@PathVariable String nit) {
        useCase.eliminar(nit);
        return ResponseEntity.noContent().build();
    }
}
