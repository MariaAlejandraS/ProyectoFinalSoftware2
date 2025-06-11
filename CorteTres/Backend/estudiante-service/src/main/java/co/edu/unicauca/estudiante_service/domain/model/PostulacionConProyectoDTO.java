package co.edu.unicauca.estudiante_service.domain.model;

import java.time.LocalDate;

public class PostulacionConProyectoDTO {
    private String id;
    private String estudianteId;
    private String proyectoId;
    private LocalDate fecha;
    private ProyectoInfoDTO proyecto;

    public PostulacionConProyectoDTO(Postulacion p, ProyectoInfoDTO proyecto) {
        this.id = p.getId();
        this.estudianteId = p.getEstudianteId();
        this.proyectoId = p.getProyectoId();
        this.fecha = p.getFecha();
        this.proyecto = proyecto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(String estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(String proyectoId) {
        this.proyectoId = proyectoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ProyectoInfoDTO getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoInfoDTO proyecto) {
        this.proyecto = proyecto;
    }

}
