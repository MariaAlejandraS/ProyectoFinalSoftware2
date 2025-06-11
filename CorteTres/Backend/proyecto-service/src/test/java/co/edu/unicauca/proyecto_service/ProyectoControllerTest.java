package co.edu.unicauca.proyecto_service;

import co.edu.unicauca.proyecto_service.application.usecase.RegistrarProyectoUseCase;
import co.edu.unicauca.proyecto_service.domain.model.EstadoProyecto;
import co.edu.unicauca.proyecto_service.domain.model.Proyecto;
import co.edu.unicauca.proyecto_service.adapters.web.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProyectoControllerTest {

    private RegistrarProyectoUseCase useCase;
    private ProyectoController controller;

    @BeforeEach
    void setUp() {
        useCase = mock(RegistrarProyectoUseCase.class);
        controller = new ProyectoController(useCase);
    }

    @Test
    void registrar_proyectoNuevo_retornaProyectoConId() {
        Proyecto proyecto = new Proyecto();
        Proyecto esperado = new Proyecto();
        esperado.setId(UUID.randomUUID().toString());

        when(useCase.registrar(any())).thenReturn(esperado);

        Proyecto resultado = controller.registrar(proyecto);

        assertNotNull(resultado.getId());
        verify(useCase).registrar(any(Proyecto.class));
    }

    @Test
    void cambiarEstado_deberiaActualizarEstadoYRetornarProyecto() {
        String id = "123";
        EstadoProyecto nuevoEstado = EstadoProyecto.ACEPTADO;
        Proyecto actualizado = new Proyecto();
        actualizado.setId(id);
        actualizado.setEstado(nuevoEstado);

        when(useCase.cambiarEstado(id, nuevoEstado, "Comentario")).thenReturn(actualizado);

        Proyecto resultado = controller.cambiarEstado(id, nuevoEstado, "Comentario");

        assertEquals(nuevoEstado, resultado.getEstado());
        verify(useCase).cambiarEstado(id, nuevoEstado, "Comentario");
    }

    @Test
    void eliminar_proyectoInvocaUseCaseYRetornaNoContent() {
        String id = "test-id";

        ResponseEntity<Void> respuesta = controller.eliminar(id);

        assertEquals(204, respuesta.getStatusCodeValue());
        verify(useCase).eliminar(id);
    }

    @Test
    void listar_devuelveListaDeProyectos() {
        List<Proyecto> lista = Arrays.asList(new Proyecto(), new Proyecto());
        when(useCase.listar()).thenReturn(lista);

        List<Proyecto> resultado = controller.listar();

        assertEquals(2, resultado.size());
    }

    @Test
    void detalles_existente_retornaOk() {
        Proyecto proyecto = new Proyecto();
        proyecto.setId("1");
        when(useCase.buscarPorId("1")).thenReturn(Optional.of(proyecto));

        ResponseEntity<Proyecto> respuesta = controller.detalles("1");

        assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        assertEquals("1", respuesta.getBody().getId());
    }

    @Test
    void detalles_inexistente_retornaNotFound() {
        when(useCase.buscarPorId("2")).thenReturn(Optional.empty());

        ResponseEntity<Proyecto> respuesta = controller.detalles("2");

        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    void listarPorEmpresa_devuelveLista() {
        List<Proyecto> proyectos = List.of(new Proyecto());
        when(useCase.listarPorEmpresa("empresa1")).thenReturn(proyectos);

        List<Proyecto> resultado = controller.listarPorEmpresa("empresa1");

        assertEquals(1, resultado.size());
    }

    @Test
    void editar_invocaUseCaseYRetornaNoContent() {
        Proyecto proyecto = new Proyecto();
        ResponseEntity<Void> respuesta = controller.editar("id", proyecto);

        assertEquals(204, respuesta.getStatusCodeValue());
        verify(useCase).editar("id", proyecto);
    }
}
