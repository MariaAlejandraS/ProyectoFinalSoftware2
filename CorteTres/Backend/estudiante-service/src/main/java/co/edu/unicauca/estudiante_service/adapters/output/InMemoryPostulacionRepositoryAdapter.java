package co.edu.unicauca.estudiante_service.adapters.output;

import co.edu.unicauca.estudiante_service.domain.model.Postulacion;
import co.edu.unicauca.estudiante_service.domain.port.PostulacionRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryPostulacionRepositoryAdapter implements PostulacionRepositoryPort {

    private final Map<String, Postulacion> db = new ConcurrentHashMap<>();

    @Override
    public Postulacion guardar(Postulacion p) {
        db.put(p.getId(), p);
        return p;
    }

    @Override
    public List<Postulacion> buscarPorEstudiante(String estudianteId) {
        return db.values().stream()
                .filter(p -> p.getEstudianteId().equals(estudianteId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Postulacion> findByEstudianteId(String estudianteId) {
        return buscarPorEstudiante(estudianteId); // Simplemente reutiliza el método ya existente
    }

    public List<Postulacion> findAll() {
        return new ArrayList<>(db.values());
    }

}
