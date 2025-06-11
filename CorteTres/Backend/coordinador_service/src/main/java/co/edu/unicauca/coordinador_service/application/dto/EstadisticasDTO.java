package co.edu.unicauca.coordinador_service.application.dto;

public class EstadisticasDTO {
    private int totalPostulados;
    private int totalAceptados;
    private int totalRechazados;
    private int totalTerminados;

    // Getters y setters
    public int getTotalPostulados() {
        return totalPostulados;
    }

    public void setTotalPostulados(int totalPostulados) {
        this.totalPostulados = totalPostulados;
    }

    public int getTotalAceptados() {
        return totalAceptados;
    }

    public void setTotalAceptados(int totalAceptados) {
        this.totalAceptados = totalAceptados;
    }

    public int getTotalRechazados() {
        return totalRechazados;
    }

    public void setTotalRechazados(int totalRechazados) {
        this.totalRechazados = totalRechazados;
    }

    public int getTotalTerminados() {
        return totalTerminados;
    }

    public void setTotalTerminados(int totalTerminados) {
        this.totalTerminados = totalTerminados;
    }

}
