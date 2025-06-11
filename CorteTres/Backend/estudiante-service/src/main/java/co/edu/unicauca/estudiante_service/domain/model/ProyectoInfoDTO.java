package co.edu.unicauca.estudiante_service.domain.model;

import java.time.LocalDate;

public class ProyectoInfoDTO {
    private String id;
    private String nombre;
    private String resumen;
    private String objetivos;
    private String descripcion;
    private int tiempoMaxMeses;
    private double presupuesto;
    private LocalDate fecha;
    private String estado;
    private String comentarios;
    private String empresaNit;

    public ProyectoInfoDTO(String id, String nombre, String resumen, String objetivos, String descripcion,
            int tiempoMaxMeses, double presupuesto, LocalDate fecha, String estado, String comentarios,
            String empresaNit) {
        this.id = id;
        this.nombre = nombre;
        this.resumen = resumen;
        this.objetivos = objetivos;
        this.descripcion = descripcion;
        this.tiempoMaxMeses = tiempoMaxMeses;
        this.presupuesto = presupuesto;
        this.fecha = fecha;
        this.estado = estado;
        this.comentarios = comentarios;
        this.empresaNit = empresaNit;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTiempoMaxMeses() {
        return tiempoMaxMeses;
    }

    public void setTiempoMaxMeses(int tiempoMaxMeses) {
        this.tiempoMaxMeses = tiempoMaxMeses;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getEmpresaNit() {
        return empresaNit;
    }

    public void setEmpresaNit(String empresaNit) {
        this.empresaNit = empresaNit;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}
