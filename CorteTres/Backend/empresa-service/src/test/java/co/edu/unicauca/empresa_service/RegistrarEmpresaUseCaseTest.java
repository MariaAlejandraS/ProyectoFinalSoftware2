package co.edu.unicauca.empresa_service;

/*
 * import org.junit.jupiter.api.BeforeEach;
 * import org.junit.jupiter.api.Test;
 * import org.mockito.Mockito;
 * 
 * import co.edu.unicauca.empresa_service.application.usecase.*;
 * import co.edu.unicauca.empresa_service.domain.model.Empresa;
 * import co.edu.unicauca.empresa_service.domain.ports.EmpresaRepository;
 * 
 * import java.util.Collections;
 * import java.util.Optional;
 * 
 * import static org.junit.jupiter.api.Assertions.*;
 * import static org.mockito.Mockito.*;
 * 
 * class RegistrarEmpresaUseCaseTest {
 * 
 * private EmpresaRepository repository;
 * private RegistrarEmpresaUseCase useCase;
 * 
 * @BeforeEach
 * void setUp() {
 * repository = Mockito.mock(EmpresaRepository.class);
 * useCase = new RegistrarEmpresaUseCase(repository);
 * }
 * 
 * @Test
 * void registrarEmpresa_exitosa() {
 * Empresa empresa = Empresa.builder()
 * .nit("123")
 * .nombre("Empresa Test")
 * .email("test@empresa.com")
 * .sector("Tecnología")
 * .telefono("123456789")
 * .nombresContacto("Juan")
 * .apellidosContacto("Pérez")
 * .cargoContacto("Gerente")
 * .build();
 * 
 * when(repository.findByNit("123")).thenReturn(Optional.empty());
 * when(repository.save(empresa)).thenReturn(empresa);
 * 
 * Empresa resultado = useCase.registrar(empresa);
 * 
 * assertNotNull(resultado);
 * assertEquals("123", resultado.getNit());
 * 
 * verify(repository, times(1)).findByNit("123");
 * verify(repository, times(1)).save(empresa);
 * }
 * 
 * @Test
 * void registrarEmpresa_yaExisteNit() {
 * Empresa empresa = Empresa.builder()
 * .nit("123")
 * .nombre("Empresa Test")
 * .email("test@empresa.com")
 * .sector("Tecnología")
 * .telefono("123456789")
 * .nombresContacto("Juan")
 * .apellidosContacto("Pérez")
 * .cargoContacto("Gerente")
 * .build();
 * 
 * when(repository.findByNit("123")).thenReturn(Optional.of(empresa));
 * 
 * IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()
 * -> {
 * useCase.registrar(empresa);
 * });
 * 
 * assertEquals("Ya existe una empresa con ese NIT", ex.getMessage());
 * verify(repository, times(1)).findByNit("123");
 * verify(repository, never()).save(any());
 * }
 * 
 * @Test
 * void listarEmpresas() {
 * Empresa empresa = Empresa.builder()
 * .nit("123")
 * .nombre("Empresa Test")
 * .email("test@empresa.com")
 * .sector("Tecnología")
 * .telefono("123456789")
 * .nombresContacto("Juan")
 * .apellidosContacto("Pérez")
 * .cargoContacto("Gerente")
 * .build();
 * 
 * when(repository.findAll()).thenReturn(Collections.singletonList(empresa));
 * 
 * var empresas = useCase.listar();
 * 
 * assertFalse(empresas.isEmpty());
 * assertEquals(1, empresas.size());
 * assertEquals("123", empresas.get(0).getNit());
 * 
 * verify(repository, times(1)).findAll();
 * }
 * }
 */